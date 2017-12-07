package org.kvlt.core.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.kvlt.core.nodes.Node;
import org.kvlt.core.nodes.NodeContainer;
import org.kvlt.core.packets.Destination;
import org.kvlt.core.utils.Finder;

public interface PacketOut extends Packet {

     void write(ByteBuf out);
     void send();

}
