// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: seeder_factory.proto

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
}