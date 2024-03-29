/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.dixmont.tests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.io7m.dixmont.colors.DmColor;
import com.io7m.dixmont.colors.DmColorModule;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DmColorSerializationTest
{
  @JsonSerialize
  @JsonDeserialize
  public record Example(
    @JsonProperty(value = "Color", required = true)
    DmColor color)
  {
    public Example
    {
      Objects.requireNonNull(color, "color");
    }
  }

  /**
   * Serialization works.
   *
   * @throws Exception On errors
   */

  @Test
  public void testSerialization()
    throws Exception
  {
    final var mapper =
      JsonMapper.builder()
        .build();

    mapper.registerModule(DmColorModule.create());

    final var v0 =
      new Example(new DmColor(0.2, 0.3, 0.4));
    final var text =
      mapper.writeValueAsString(v0);

    assertEquals("{\"Color\":\"#334c66\"}", text);

    final var rec =
      mapper.readValue(text, Example.class);

    assertEquals(v0.toString(), rec.toString());
  }
}
