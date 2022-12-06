/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gbif.metadata.eml.handler;

import org.gbif.metadata.eml.BasicMetadataImpl;

/**
 * A simple metadata sax base handler that collects all character data inside elements into a string buffer, resetting
 * the buffer with every element start and storing the string version of the buffer in this.content when the end of the
 * element is reached.
 * Make sure to call the super methods when implementing this handler!
 */
public abstract class BasicMetadataSaxHandler extends SimpleSaxHandler {

  protected BasicMetadataImpl bm;
  protected static final String NS_DC = "http://purl.org/dc/terms/";
  protected static final String NS_DCTERMS = "http://purl.org/dc/elements/1.1/";

  @Override
  public void startDocument() {
    super.startDocument();
    bm = new BasicMetadataImpl();
  }

  public BasicMetadataImpl yield() {
    return bm;
  }
}
