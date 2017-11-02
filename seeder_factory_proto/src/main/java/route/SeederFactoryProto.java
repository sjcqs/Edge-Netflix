// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/seeder_factory.proto

package route;

public final class SeederFactoryProto {
  private SeederFactoryProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_KeywordsMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_KeywordsMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_SizeMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_SizeMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_VideoMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_VideoMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_EndpointMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_EndpointMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_SeederMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_SeederMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n#src/main/proto/seeder_factory.proto\022\005r" +
      "oute\"\"\n\017KeywordsMessage\022\017\n\007keyword\030\001 \003(\t" +
      "\",\n\013SizeMessage\022\r\n\005width\030\001 \001(\005\022\016\n\006height" +
      "\030\002 \001(\005\"`\n\014VideoMessage\022\014\n\004name\030\001 \001(\t\022 \n\004" +
      "size\030\002 \001(\0132\022.route.SizeMessage\022\017\n\007bitrat" +
      "e\030\003 \001(\005\022\017\n\007keyword\030\004 \003(\t\">\n\017EndpointMess" +
      "age\022\021\n\ttransport\030\001 \001(\t\022\n\n\002ip\030\002 \001(\t\022\014\n\004po" +
      "rt\030\003 \001(\005\"]\n\rSeederMessage\022\"\n\005video\030\001 \001(\013" +
      "2\023.route.VideoMessage\022(\n\010endpoint\030\002 \001(\0132" +
      "\026.route.EndpointMessage2\317\001\n\rSeederFactor",
      "y\022>\n\014CreateSeeder\022\026.route.KeywordsMessag" +
      "e\032\024.route.SeederMessage\"\000\022?\n\013ListSeeders" +
      "\022\026.route.KeywordsMessage\032\024.route.SeederM" +
      "essage\"\0000\001\022=\n\nListVideos\022\026.route.Keyword" +
      "sMessage\032\023.route.VideoMessage\"\0000\001B#\n\005rou" +
      "teB\022SeederFactoryProtoP\001\242\002\003RTGb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_route_KeywordsMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_route_KeywordsMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_KeywordsMessage_descriptor,
        new java.lang.String[] { "Keyword", });
    internal_static_route_SizeMessage_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_route_SizeMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_SizeMessage_descriptor,
        new java.lang.String[] { "Width", "Height", });
    internal_static_route_VideoMessage_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_route_VideoMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_VideoMessage_descriptor,
        new java.lang.String[] { "Name", "Size", "Bitrate", "Keyword", });
    internal_static_route_EndpointMessage_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_route_EndpointMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_EndpointMessage_descriptor,
        new java.lang.String[] { "Transport", "Ip", "Port", });
    internal_static_route_SeederMessage_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_route_SeederMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_SeederMessage_descriptor,
        new java.lang.String[] { "Video", "Endpoint", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
