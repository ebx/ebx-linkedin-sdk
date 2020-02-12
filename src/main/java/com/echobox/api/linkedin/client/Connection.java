/**
 * Copyright (c) 2010-2017 Mark Allen, Norbert Bartels.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
//Source - https://restfb.com/

package com.echobox.api.linkedin.client;

import static java.util.Collections.unmodifiableList;

import com.echobox.api.linkedin.client.paging.PagingStrategy;
import com.echobox.api.linkedin.exception.LinkedInJsonMappingException;
import com.echobox.api.linkedin.util.ReflectionUtils;
import com.echobox.api.linkedin.version.Version;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a LinkedIn API Connection type
 *
 * @param <T> The LinkedIn type
 * @author Joanna
 */
public class Connection<T> implements Iterable<List<T>> {
  private LinkedInClient linkedinClient;
  private Class<T> connectionType;
  private List<T> data;
  private String previousPageUrl;
  private String nextPageUrl;

  /**
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public ConnectionIterator<T> iterator() {
    return new Itr<T>(this);
  }

  /**
   * Iterator over connection pages.
   * @param <T> type
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   */
  protected static class Itr<T> implements ConnectionIterator<T> {
    private Connection<T> connection;
    private boolean initialPage = true;

    /**
     * Creates a new iterator over the given {@code connection}.
     * 
     * @param connection
     *          The connection over which to iterate.
     */
    protected Itr(Connection<T> connection) {
      this.connection = connection;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      // Special case: initial page will always have data
      return initialPage || connection.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public List<T> next() {
      // Special case: initial page will always have data, return it
      // immediately.
      if (initialPage) {
        initialPage = false;
        return connection.getData();
      }

      if (!connection.hasNext()) {
        throw new NoSuchElementException("There are no more pages in the connection.");
      }

      connection = connection.fetchNextPage();
      return connection.getData();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException(Itr.class.getSimpleName()
          + " doesn't support the remove() operation.");
    }

    /**
     * @see ConnectionIterator#snapshot()
     */
    @Override
    public Connection<T> snapshot() {
      return connection;
    }
  }

  /**
   * Creates a connection with the given {@code jsonObject}.
   * 
   * @param fullEndpoint
   *          The full endpoint so it is possible to build up the next and previous pages for the
   *          URL
   * @param linkedinClient
   *          The {@code LinkedInClient} used to fetch additional pages and map data to JSON
   *          objects.
   * @param json
   *          Raw JSON which must include a {@code data} field that holds a JSON array and
   *          optionally a {@code paging} field that holds a JSON object with next/previous page
   *          URLs.
   * @param connectionType
   *          Connection type token.
   * @throws LinkedInJsonMappingException
   *           If the provided {@code json} is invalid.
   */
  @SuppressWarnings("unchecked")
  public Connection(String fullEndpoint, LinkedInClient linkedinClient, String json,
      Class<T> connectionType) {
    List<T> dataList = new ArrayList<>();

    if (json == null) {
      throw new LinkedInJsonMappingException("You must supply non-null connection JSON.");
    }

    JsonObject jsonObject;

    try {
      jsonObject = Json.parse(json).asObject();
    } catch (ParseException e) {
      throw new LinkedInJsonMappingException("The connection JSON you provided was invalid: "
          + json, e);
    }
    
    Version version = linkedinClient.getVersion();
    PagingStrategy pagingStrategy = version.getPagingStrategy();
  
    // Pull out data
    JsonArray jsonData = jsonObject.get(pagingStrategy.getDataKey()).asArray();
    for (int i = 0; i < jsonData.size(); i++) {
      dataList.add(connectionType.equals(JsonObject.class) ? (T) jsonData.get(i)
          : linkedinClient.getJsonMapper().toJavaObject(jsonData.get(i).toString(),
              connectionType));
    }

    pagingStrategy.populatePages(jsonObject, fullEndpoint);
    this.nextPageUrl = pagingStrategy.getNextPageUrl();
    this.previousPageUrl = pagingStrategy.getPreviousPageUrl();

    this.data = unmodifiableList(dataList);
    this.linkedinClient = linkedinClient;
    this.connectionType = connectionType;
  }

  /**
   * Fetches the next page of the connection. Designed to be used by {@link Itr}.
   * 
   * @return The next page of the connection.
   */
  protected Connection<T> fetchNextPage() {
    return linkedinClient.fetchConnectionPage(getNextPageUrl(), connectionType);
  }

  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  @Override
  public boolean equals(Object object) {
    return ReflectionUtils.equals(this, object);
  }

  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * Data for this connection.
   * 
   * @return Data for this connection.
   */
  public List<T> getData() {
    return data;
  }

  /**
   * This connection's "previous page of data" URL.
   * 
   * @return This connection's "previous page of data" URL, or {@code null} if there is no previous
   * page.
   */
  public String getPreviousPageUrl() {
    return previousPageUrl;
  }

  /**
   * This connection's "next page of data" URL.
   * 
   * @return This connection's "next page of data" URL, or {@code null} if there is no next page.
   */
  public String getNextPageUrl() {
    return nextPageUrl;
  }

  /**
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this connection, {@code false}
   * otherwise.
   */
  public boolean hasPrevious() {
    return StringUtils.isNotBlank(getPreviousPageUrl());
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection, {@code false}
   * otherwise.
   */
  public boolean hasNext() {
    return StringUtils.isNotBlank(getNextPageUrl());
  }
}
