/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2022 L3g7
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.l3g7.griefer_utils.util;

import com.google.gson.*;
import dev.l3g7.griefer_utils.misc.ThrowingSupplier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import static dev.l3g7.griefer_utils.util.Util.elevate;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * A utility class for simplified file and network operations.
 * All operations use an encoding of UTF-8.
 */
public class IOUtil {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final JsonParser jsonParser = new JsonParser();

	/**
	 * @return A wrapper class for reading the contents of the given file.
	 */
	public static FileReadOperation read(File file) {
		return new FileReadOperation(file);
	}

	/**
	 * Writes the content to the file.
	 */
	public static void write(File file, String content) {
		file.getParentFile().mkdirs();

		try {
			Files.write(file.toPath(), content.getBytes(UTF_8));
		} catch (IOException e) {
			throw elevate(e);
		}
	}

	/**
	 * Writes the content it to the file using pretty printing.
	 */
	public static void writeJson(File file, JsonElement content) {
		write(file, gson.toJson(content));
	}

	/**
	 * A wrapper class for reading the contents of a file.
	 */
	public static class FileReadOperation extends ReadOperation {

		private final File file;

		private FileReadOperation(File file) {
			this.file = file;
		}

		@Override
		protected InputStream open() throws Exception {
			return Files.newInputStream(file.toPath(), StandardOpenOption.READ);
		}

	}

	/**
	 * A wrapper class for reading the contents of an url.
	 */
	public static class URLReadOperation extends ReadOperation {

		private final String url;

		private URLReadOperation(String url) {
			this.url = url;
		}

		@Override
		protected InputStream open() throws Exception {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.addRequestProperty("User-Agent", "GrieferUtils");
			return conn.getInputStream();
		}
	}

	public abstract static class ReadOperation {

		protected abstract InputStream open() throws Exception;

		/**
		 * Tries to read the input stream as a json object.
		 */
		public Optional<JsonObject> asJsonObject() {
			return catchErrors(() -> {
				try (InputStreamReader in = new InputStreamReader(open(), UTF_8)) {
					return jsonParser.parse(in).getAsJsonObject();
				}
			});
		}

		/**
		 * @return the value given by the supplier or an empty optional if the supplier throws an error.
		 */
		private <V> Optional<V> catchErrors(ThrowingSupplier<V> supplier) {
			try {
				return Optional.of(supplier.run());
			} catch (Exception e) {
				e.printStackTrace();
				return Optional.empty();
			}
		}
	}

}