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

package com.clubobsidian.dynamicgui.api.manager;

import javax.inject.Inject;


public abstract class MiniMessageManager {

    @Inject
    private static MiniMessageManager instance;

    public static MiniMessageManager get() {
        return instance;
    }

    /**
     * Converts a minimessage message to json
     *
     * @param message message to convert to json
     * @return the converted json
     */
    public abstract String toJson(String message);

}