/*******************************************************************************
 * Copyright (c) 2014 Sebastian Stenzel, Markus Kreusch
 * This file is licensed under the terms of the MIT license.
 * See the LICENSE.txt file for more info.
 * 
 * Contributors:
 *     Sebastian Stenzel - initial API and implementation
 *     Markus Kreusch - Refactored WebDavMounter to use strategy pattern
 ******************************************************************************/
package org.cryptomator.ui.util.mount;

import java.net.URI;

import org.apache.commons.lang3.SystemUtils;
import org.cryptomator.ui.util.command.Script;

final class LinuxGvfsWebDavMounter implements WebDavMounterStrategy {

	@Override
	public boolean shouldWork() {
		if (SystemUtils.IS_OS_LINUX) {
			final Script checkScripts = Script.fromLines("which gvfs-mount xdg-open");
			try {
				checkScripts.execute();
				return true;
			} catch (CommandFailedException e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public void warmUp(int serverPort) {
		// no-op
	}

	@Override
	public WebDavMount mount(URI uri, String name) throws CommandFailedException {
		final Script mountScript = Script.fromLines(
				"set -x",
				"gvfs-mount \"dav://localhost:$DAV_PORT$DAV_PATH\"",
				"xdg-open \"dav://localhost:$DAV_PORT$DAV_PATH\"")
				.addEnv("DAV_PORT", String.valueOf(uri.getPort()))
				.addEnv("DAV_PATH", uri.getRawPath());
		final Script unmountScript = Script.fromLines(
				"set -x",
				"gvfs-mount -u \"dav://localhost:$DAV_PORT$DAV_PATH\"")
				.addEnv("DAV_PORT", String.valueOf(uri.getPort()))
				.addEnv("DAV_PATH", uri.getRawPath());
		mountScript.execute();
		return new WebDavMount() {
			@Override
			public void unmount() throws CommandFailedException {
				unmountScript.execute();
			}
		};
	}

}
