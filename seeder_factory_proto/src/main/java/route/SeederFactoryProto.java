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
    internal_static_route_ListQuery_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_ListQuery_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_Video_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_Video_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_route_Seeder_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_route_Seeder_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n#src/main/proto/seeder_factory.proto\022\005r" +
      "oute\"\035\n\tListQuery\022\020\n\010keywords\030\001 \001(\t\"\025\n\005V" +
      "ideo\022\014\n\004name\030\001 \001(\t\"\026\n\006Seeder\022\014\n\004name\030\001 \001" +
      "(\t2r\n\rSeederFactory\022-\n\014CreateSeeder\022\014.ro" +
      "ute.Video\032\r.route.Seeder\"\000\0222\n\013ListSeeder" +
      "s\022\020.route.ListQuery\032\r.route.Seeder\"\0000\001B#" +
      "\n\005routeB\022SeederFactoryProtoP\001\242\002\003RTGb\006pro" +
      "to3"
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
    internal_static_route_ListQuery_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_route_ListQuery_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_ListQuery_descriptor,
        new java.lang.String[] { "Keywords", });
    internal_static_route_Video_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_route_Video_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_Video_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_route_Seeder_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_route_Seeder_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_route_Seeder_descriptor,
        new java.lang.String[] { "Name", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}