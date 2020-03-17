/*
 * Copyright 2020 Google LLC
 *
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
package com.google.solutions.df.video.analytics.common;

import com.google.auto.value.AutoValue;
import com.google.solutions.df.video.analytics.common.AnnotationRequestTransform.Builder;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutoValue
public abstract class ResponseWriteTransform extends PTransform<PCollection<String>, PDone> {

  public static final Logger LOG = LoggerFactory.getLogger(ResponseWriteTransform.class);

  public abstract String topic();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setTopic(String topic);

    public abstract ResponseWriteTransform build();
  }

  public static Builder newBuilder() {
    return new AutoValue_ResponseWriteTransform.Builder();
  }

  @Override
  public PDone expand(PCollection<String> input) {
    return input.apply("WriteToTopic", PubsubIO.writeStrings().to(topic()));
  }
}
