package org.rocket.request;

import org.rocket.general.Body;
import org.rocket.general.Headers;
import org.rocket.general.Parameters;

public interface HttpRequest {

    Headers headers();

    Body body();

    Parameters params();
}
