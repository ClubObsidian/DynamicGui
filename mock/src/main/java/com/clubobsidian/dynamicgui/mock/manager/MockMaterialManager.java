/*
 *    Copyright 2022 virustotalop and contributors.
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

package com.clubobsidian.dynamicgui.mock.manager;

import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;

import java.util.List;

public class MockMaterialManager extends MaterialManager {
    @Override
    public List<String> getMaterials() {
        return null;
    }

    @Override
    public String normalizeMaterial(String material) {
        if (material == null) {
            return null;
        }
        return material.toUpperCase();
    }
}
