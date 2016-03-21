package org.devefx.wx.common.xml;

import org.w3c.dom.Node;

public interface Nodelet {
	void process (Node node) throws Exception;
}
