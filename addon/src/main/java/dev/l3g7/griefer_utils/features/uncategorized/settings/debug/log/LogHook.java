/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2023 L3g7
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

package dev.l3g7.griefer_utils.features.uncategorized.settings.debug.log;

import dev.l3g7.griefer_utils.util.reflection.Reflection;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class LogHook {

	public static final Path FILE = Paths.get(System.getProperty("java.io.tmpdir"), "griefer_utils_debug_log.txt");
	private static OutputStream fileOut;

	public static void hook() {
		try {
			fileOut = Files.newOutputStream(FILE);
			OutputStream sOut = findLowestStream();
			OutputStream out = Reflection.get(sOut, "out");
			Reflection.set(sOut, new OutputStream() {
				public void write(int b) throws IOException {
					try {
						out.write(b);
						fileOut.write(b);
					} catch (Throwable t) {
						t.printStackTrace(new PrintStream(sOut));
						throw new RuntimeException(t);
					}
				}
			}, "out");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static OutputStream findLowestStream() {
		OutputStream sOut = System.out;
		while (true) {
			OutputStream child = Reflection.get(sOut, "out");
			if (!(child instanceof FilterOutputStream))
				break;

			sOut = child;
		}
		return sOut;
	}

	public static void flush() {
		try {
			fileOut.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}