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
import org.kurento.client.internal.NotEnoughResourcesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

/**
 * User session.
 * 
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @since 6.2.2
 */
public class UserSession {

  private final Logger log = LoggerFactory.getLogger(UserSession.class);

  private WebRtcEndpoint webRtcEndpoint;
  private MediaPipeline mediaPipeline;
  private KurentoClient kurentoClient;
  private String sessionId;

  public UserSession(String sessionId) {
    this.sessionId = sessionId;
    kurentoClient = KurentoClient.create();

    log.debug("kurentoClient {}", kurentoClient);

    // TODO this exception should be raised by nubomedia-media-client
    if (kurentoClient == null) {
      throw new NotEnoughResourcesException("Not enough resources (kurentoClient is null)");
    }

    log.info("Created kurentoClient (session {})", sessionId);

    mediaPipeline = getKurentoClient().createMediaPipeline();
    log.info("Created Media Pipeline {} (session {})", getMediaPipeline().getId(), sessionId);

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
    log.info("Releasing media pipeline {} (session {})", getMediaPipeline().getId(), sessionId);
    getMediaPipeline().release();
    log.info("Destroying kurentoClient (session {})", sessionId);
    getKurentoClient().destroy();
  }

  public String getSessionId() {
    return sessionId;
  }
}
