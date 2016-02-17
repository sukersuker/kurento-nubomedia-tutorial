/*
 * (C) Copyright 2016 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */
package org.kurento.tutorial.magicmirror;

import org.kurento.client.IceCandidate;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;

import com.google.gson.JsonObject;

/**
 * User session.
 * 
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 6.2.2
 */
public class UserSession {
  private WebRtcEndpoint webRtcEndpoint;
  private MediaPipeline mediaPipeline;
  private KurentoClient kurentoClient;

  public UserSession() {
    kurentoClient = KurentoClient.create();
    mediaPipeline = getKurentoClient().createMediaPipeline();
    webRtcEndpoint = new WebRtcEndpoint.Builder(getMediaPipeline()).build();
  }

  public WebRtcEndpoint getWebRtcEndpoint() {
    return webRtcEndpoint;
  }

  public MediaPipeline getMediaPipeline() {
    return mediaPipeline;
  }

  public KurentoClient getKurentoClient() {
    return kurentoClient;
  }

  public void addCandidate(IceCandidate candidate) {
    getWebRtcEndpoint().addIceCandidate(candidate);
  }

  public void addCandidate(JsonObject jsonCandidate) {
    IceCandidate candidate = new IceCandidate(jsonCandidate.get("candidate").getAsString(),
        jsonCandidate.get("sdpMid").getAsString(), jsonCandidate.get("sdpMLineIndex").getAsInt());
    getWebRtcEndpoint().addIceCandidate(candidate);
  }

  public void release() {
    getMediaPipeline().release();
    getKurentoClient().destroy();
  }
}
