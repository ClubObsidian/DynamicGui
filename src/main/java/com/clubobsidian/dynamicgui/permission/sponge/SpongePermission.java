/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.permission.sponge;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.permission.Permission;

public class SpongePermission implements Permission {

	@Override
	public boolean setup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPermission(PlayerWrapper<?> playerWrapper, String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPemission(PlayerWrapper<?> playerWrapper, String permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePermission(PlayerWrapper<?> playerWrapper, String permission) {
		// TODO Auto-generated method stub
		return false;
	}
}