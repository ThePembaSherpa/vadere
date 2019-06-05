package org.vadere.manager.stsc;

import org.vadere.manager.stsc.sumo.TrafficLightPhase;
import org.vadere.util.geometry.shapes.VPoint;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.List;

public interface TraCIWriter {


	ByteBuffer asByteBuffer();

	byte[] asByteArray();

	TraCIWriter rest();

	TraCIWriter writeUnsignedByteWithId(int val);
	TraCIWriter writeByteWithId(byte val);
	TraCIWriter writeIntWithId(int val);
	TraCIWriter writeDoubleWithId(double val);
	TraCIWriter writeStringWithId(String val);
	TraCIWriter writeStringListWithId(List<String> val);

	TraCIWriter writeByte(int val);

	default TraCIWriter writeUnsignedByte(int val){
		if (val>= 0 && val<=255){
			writeByte(val);
		} else {
			throw new IllegalArgumentException(
					"unsignedByte must be within (including) 0..255 but was: " + val);
		}
		return this;
	}

	TraCIWriter writeBytes(byte[] buf);
	TraCIWriter writeBytes(byte[] buf, int offset, int len);

	default TraCIWriter writeBytes(ByteBuffer buf, int offset, int len){
		writeBytes(buf.array(), offset, len);
		return this;
	}
	default TraCIWriter writeBytes(ByteBuffer buf){
		writeBytes(buf, 0, buf.array().length);
		return this;
	}

	default TraCIWriter writeInt(int val){
		writeBytes(ByteBuffer.allocate(4).putInt(val).array());
		return this;
	}

	default TraCIWriter writeDouble(double val){
		writeBytes(ByteBuffer.allocate(8).putDouble(val).array());
		return this;
	}

	TraCIWriter writeString(String val);

	TraCIWriter writeStringList(List<String> val);

	TraCIWriter write2DPosition(double x, double y);

	TraCIWriter write3DPosition(double x, double y, double z);

	TraCIWriter writeRoadMapPosition(String roadId, double pos, int laneId);

	TraCIWriter writeLonLatPosition(double lon, double lat);

	TraCIWriter writeLonLatAltPosition(double lon, double lat, double alt);

	TraCIWriter writePolygon(VPoint... points);

	TraCIWriter writePolygon(List<VPoint> points);

	TraCIWriter writeTrafficLightPhaseList(List<TrafficLightPhase> phases);

	TraCIWriter writeColor(Color color);

	TraCIWriter writeCommandLength(int cmdLen);

	int stringByteCount(String str);

	int size();
	int getStringByteCount(String val);

}
