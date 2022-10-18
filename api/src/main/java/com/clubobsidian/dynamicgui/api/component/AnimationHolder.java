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

package com.clubobsidian.dynamicgui.api.component;

public interface AnimationHolder extends Refreshable {

    /**
     * The current tick for the animation holder
     *
     * @return the current tick
     */
    int getCurrentTick();

    /**
     * Resets the current tick
     */
    void resetTick();

    /**
     * Ticks the animation holder
     *
     * @return the tick after the previous value was incremented
     */
    int tick();

    /**
     * The current frame, a frame is current ticks / 20
     *
     * @return the current frame
     */
    int getFrame();

    /**
     * Resets the frame
     */
    void resetFrame();

}