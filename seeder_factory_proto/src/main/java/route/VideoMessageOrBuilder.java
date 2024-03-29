// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/seeder_factory.proto

package route;

public interface VideoMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:route.VideoMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <code>string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  boolean hasSize();
  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  route.SizeMessage getSize();
  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  route.SizeMessageOrBuilder getSizeOrBuilder();

  /**
   * <code>int32 bitrate = 3;</code>
   */
  int getBitrate();

  /**
   * <code>repeated string keyword = 4;</code>
   */
  java.util.List<java.lang.String>
      getKeywordList();
  /**
   * <code>repeated string keyword = 4;</code>
   */
  int getKeywordCount();
  /**
   * <code>repeated string keyword = 4;</code>
   */
  java.lang.String getKeyword(int index);
  /**
   * <code>repeated string keyword = 4;</code>
   */
  com.google.protobuf.ByteString
      getKeywordBytes(int index);

  /**
   * <code>int64 length = 5;</code>
   */
  long getLength();

  /**
   * <code>string filename = 6;</code>
   */
  java.lang.String getFilename();
  /**
   * <code>string filename = 6;</code>
   */
  com.google.protobuf.ByteString
      getFilenameBytes();

  /**
   * <code>string checksum = 7;</code>
   */
  java.lang.String getChecksum();
  /**
   * <code>string checksum = 7;</code>
   */
  com.google.protobuf.ByteString
      getChecksumBytes();

  /**
   * <code>repeated string checksums = 8;</code>
   */
  java.util.List<java.lang.String>
      getChecksumsList();
  /**
   * <code>repeated string checksums = 8;</code>
   */
  int getChecksumsCount();
  /**
   * <code>repeated string checksums = 8;</code>
   */
  java.lang.String getChecksums(int index);
  /**
   * <code>repeated string checksums = 8;</code>
   */
  com.google.protobuf.ByteString
      getChecksumsBytes(int index);
}
