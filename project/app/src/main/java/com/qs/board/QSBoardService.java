// Copyright 2016 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.qs.board;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.service.quicksettings.TileService;
import android.util.Log;

@SuppressLint("Override")
public class QSBoardService
        extends TileService {

    /**
     * Called when the tile is added to the Quick Settings.
     *
     * @return TileService constant indicating tile state
     */

    @Override
    public void onTileAdded() {

    }

    /**
     * Called when this tile begins listening for events.
     */
    @Override
    public void onStartListening() {

    }

    /**
     * Called when the user taps the tile.
     */
    @Override
    public void onClick() {

        Intent dialog = new Intent(this, DialogActivity.class);

        startActivityAndCollapse(dialog);
    }

    /**
     * Called when this tile moves out of the listening state.
     */
    @Override
    public void onStopListening() {
        Log.d("QS", "Stop Listening");
    }

    /**
     * Called when the user removes this tile from Quick Settings.
     */
    @Override
    public void onTileRemoved() {
        Log.d("QS", "Tile removed");
    }
}
