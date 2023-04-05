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

package com.clubobsidian.dynamicgui.api.component;

import org.jetbrains.annotations.Nullable;

public interface CloseableComponent {

    /**
     * Gets whether the component can close
     *
     * @return true if should close, false if should not close or null if non-existent which defaults to parent behavior
     */
    @Nullable Boolean getClose();

    /**
     * Sets whether the component should close
     *
     * @param close boxed boolean if the component should close
     */
    void setClose(@Nullable Boolean close);

}