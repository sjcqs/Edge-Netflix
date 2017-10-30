package route;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: seeder_factory.proto")
public final class SeederFactoryGrpc {

  private SeederFactoryGrpc() {}

  public static final String SERVICE_NAME = "route.SeederFactory";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<route.VideoMessage,
      route.SeederMessage> METHOD_CREATE_SEEDER =
      io.grpc.MethodDescriptor.<route.VideoMessage, route.SeederMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "route.SeederFactory", "CreateSeeder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              route.VideoMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              route.SeederMessage.getDefaultInstance()))
          .setSchemaDescriptor(new SeederFactoryMethodDescriptorSupplier("CreateSeeder"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<route.KeywordsMessage,
      route.SeederMessage> METHOD_LIST_SEEDERS =
      io.grpc.MethodDescriptor.<route.KeywordsMessage, route.SeederMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "route.SeederFactory", "ListSeeders"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              route.KeywordsMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              route.SeederMessage.getDefaultInstance()))
          .setSchemaDescriptor(new SeederFactoryMethodDescriptorSupplier("ListSeeders"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SeederFactoryStub newStub(io.grpc.Channel channel) {
    return new SeederFactoryStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SeederFactoryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SeederFactoryBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SeederFactoryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SeederFactoryFutureStub(channel);
  }

  /**
   */
  public static abstract class SeederFactoryImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Portal -&gt; SeederFactory
     * Ask to create the seeder for the given video and returns the created seeder in return
     * </pre>
     */
    public void createSeeder(route.VideoMessage request,
        io.grpc.stub.StreamObserver<route.SeederMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SEEDER, responseObserver);
    }

    /**
     * <pre>
     * Send the list of seeders
     * </pre>
     */
    public void listSeeders(route.KeywordsMessage request,
        io.grpc.stub.StreamObserver<route.SeederMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SEEDERS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_SEEDER,
            asyncUnaryCall(
              new MethodHandlers<
                route.VideoMessage,
                route.SeederMessage>(
                  this, METHODID_CREATE_SEEDER)))
          .addMethod(
            METHOD_LIST_SEEDERS,
            asyncServerStreamingCall(
              new MethodHandlers<
                route.KeywordsMessage,
                route.SeederMessage>(
                  this, METHODID_LIST_SEEDERS)))
          .build();
    }
  }

  /**
   */
  public static final class SeederFactoryStub extends io.grpc.stub.AbstractStub<SeederFactoryStub> {
    private SeederFactoryStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SeederFactoryStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeederFactoryStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SeederFactoryStub(channel, callOptions);
    }

    /**
     * <pre>
     * Portal -&gt; SeederFactory
     * Ask to create the seeder for the given video and returns the created seeder in return
     * </pre>
     */
    public void createSeeder(route.VideoMessage request,
        io.grpc.stub.StreamObserver<route.SeederMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SEEDER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Send the list of seeders
     * </pre>
     */
    public void listSeeders(route.KeywordsMessage request,
        io.grpc.stub.StreamObserver<route.SeederMessage> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_LIST_SEEDERS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SeederFactoryBlockingStub extends io.grpc.stub.AbstractStub<SeederFactoryBlockingStub> {
    private SeederFactoryBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SeederFactoryBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeederFactoryBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SeederFactoryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Portal -&gt; SeederFactory
     * Ask to create the seeder for the given video and returns the created seeder in return
     * </pre>
     */
    public route.SeederMessage createSeeder(route.VideoMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SEEDER, getCallOptions(), request);
    }

    /**
     * <pre>
     * Send the list of seeders
     * </pre>
     */
    public java.util.Iterator<route.SeederMessage> listSeeders(
        route.KeywordsMessage request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_LIST_SEEDERS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SeederFactoryFutureStub extends io.grpc.stub.AbstractStub<SeederFactoryFutureStub> {
    private SeederFactoryFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SeederFactoryFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeederFactoryFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SeederFactoryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Portal -&gt; SeederFactory
     * Ask to create the seeder for the given video and returns the created seeder in return
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<route.SeederMessage> createSeeder(
        route.VideoMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SEEDER, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SEEDER = 0;
  private static final int METHODID_LIST_SEEDERS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SeederFactoryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SeederFactoryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SEEDER:
          serviceImpl.createSeeder((route.VideoMessage) request,
              (io.grpc.stub.StreamObserver<route.SeederMessage>) responseObserver);
          break;
        case METHODID_LIST_SEEDERS:
          serviceImpl.listSeeders((route.KeywordsMessage) request,
              (io.grpc.stub.StreamObserver<route.SeederMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SeederFactoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SeederFactoryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return route.SeederFactoryProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SeederFactory");
    }
  }

  private static final class SeederFactoryFileDescriptorSupplier
      extends SeederFactoryBaseDescriptorSupplier {
    SeederFactoryFileDescriptorSupplier() {}
  }

  private static final class SeederFactoryMethodDescriptorSupplier
      extends SeederFactoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SeederFactoryMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SeederFactoryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SeederFactoryFileDescriptorSupplier())
              .addMethod(METHOD_CREATE_SEEDER)
              .addMethod(METHOD_LIST_SEEDERS)
              .build();
        }
      }
    }
    return result;
  }
}
