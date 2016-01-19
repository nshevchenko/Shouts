package com.nik.shouts.interfaces;

import com.nik.shouts.models.Shout;

import java.io.Serializable;

/**
 * Created by nik on 14/12/15.
 */

public interface RequestCallback extends Serializable {
        public void onRequestComplete(String result);
}
