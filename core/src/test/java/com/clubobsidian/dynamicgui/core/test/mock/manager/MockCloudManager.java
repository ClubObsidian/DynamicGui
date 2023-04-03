/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.core.test.mock.manager;

import com.clubobsidian.dynamicgui.core.command.cloud.CloudArgument;
import com.clubobsidian.dynamicgui.core.manager.cloud.CloudManager;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayer;

public class MockCloudManager implements CloudManager {
    @Override
    public CloudArgument createPlayerArg() {
        return CloudArgument.create(MockPlayer.class);
    }
}
