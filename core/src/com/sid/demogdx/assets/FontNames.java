package com.sid.demogdx.assets;

/**
 * Created by SID on 2017-03-31.
 */
// TODO: 2017-03-31 perhaps delete this class?
@Deprecated
public class FontNames {

    public enum FontSize {
        SIZE_32(32), SIZE_48(48), SIZE_64(64);
        private int size;

        FontSize(int size) {
            this.size = size;
        }

        public int size() {
            return size;
        }
    }
}
