package me.endergaming.common.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: communication.proto")
public final class CommunicationsGrpc {

  private CommunicationsGrpc() {}

  public static final String SERVICE_NAME = "Communications";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.PlayerStatsRequest,
      me.endergaming.common.grpc.Communication.Stats> getGetStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStats",
      requestType = me.endergaming.common.grpc.Communication.PlayerStatsRequest.class,
      responseType = me.endergaming.common.grpc.Communication.Stats.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.PlayerStatsRequest,
      me.endergaming.common.grpc.Communication.Stats> getGetStatsMethod() {
    io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.PlayerStatsRequest, me.endergaming.common.grpc.Communication.Stats> getGetStatsMethod;
    if ((getGetStatsMethod = CommunicationsGrpc.getGetStatsMethod) == null) {
      synchronized (CommunicationsGrpc.class) {
        if ((getGetStatsMethod = CommunicationsGrpc.getGetStatsMethod) == null) {
          CommunicationsGrpc.getGetStatsMethod = getGetStatsMethod = 
              io.grpc.MethodDescriptor.<me.endergaming.common.grpc.Communication.PlayerStatsRequest, me.endergaming.common.grpc.Communication.Stats>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Communications", "GetStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.endergaming.common.grpc.Communication.PlayerStatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.endergaming.common.grpc.Communication.Stats.getDefaultInstance()))
                  .setSchemaDescriptor(new CommunicationsMethodDescriptorSupplier("GetStats"))
                  .build();
          }
        }
     }
     return getGetStatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.Empty,
      me.endergaming.common.grpc.Communication.Server_Info> getGetServerInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetServerInfo",
      requestType = me.endergaming.common.grpc.Communication.Empty.class,
      responseType = me.endergaming.common.grpc.Communication.Server_Info.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.Empty,
      me.endergaming.common.grpc.Communication.Server_Info> getGetServerInfoMethod() {
    io.grpc.MethodDescriptor<me.endergaming.common.grpc.Communication.Empty, me.endergaming.common.grpc.Communication.Server_Info> getGetServerInfoMethod;
    if ((getGetServerInfoMethod = CommunicationsGrpc.getGetServerInfoMethod) == null) {
      synchronized (CommunicationsGrpc.class) {
        if ((getGetServerInfoMethod = CommunicationsGrpc.getGetServerInfoMethod) == null) {
          CommunicationsGrpc.getGetServerInfoMethod = getGetServerInfoMethod = 
              io.grpc.MethodDescriptor.<me.endergaming.common.grpc.Communication.Empty, me.endergaming.common.grpc.Communication.Server_Info>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "Communications", "GetServerInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.endergaming.common.grpc.Communication.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  me.endergaming.common.grpc.Communication.Server_Info.getDefaultInstance()))
                  .setSchemaDescriptor(new CommunicationsMethodDescriptorSupplier("GetServerInfo"))
                  .build();
          }
        }
     }
     return getGetServerInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CommunicationsStub newStub(io.grpc.Channel channel) {
    return new CommunicationsStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CommunicationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CommunicationsBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CommunicationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CommunicationsFutureStub(channel);
  }

  /**
   */
  public static abstract class CommunicationsImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStats(me.endergaming.common.grpc.Communication.PlayerStatsRequest request,
        io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Stats> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStatsMethod(), responseObserver);
    }

    /**
     */
    public void getServerInfo(me.endergaming.common.grpc.Communication.Empty request,
        io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Server_Info> responseObserver) {
      asyncUnimplementedUnaryCall(getGetServerInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStatsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                me.endergaming.common.grpc.Communication.PlayerStatsRequest,
                me.endergaming.common.grpc.Communication.Stats>(
                  this, METHODID_GET_STATS)))
          .addMethod(
            getGetServerInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                me.endergaming.common.grpc.Communication.Empty,
                me.endergaming.common.grpc.Communication.Server_Info>(
                  this, METHODID_GET_SERVER_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class CommunicationsStub extends io.grpc.stub.AbstractStub<CommunicationsStub> {
    private CommunicationsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommunicationsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommunicationsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommunicationsStub(channel, callOptions);
    }

    /**
     */
    public void getStats(me.endergaming.common.grpc.Communication.PlayerStatsRequest request,
        io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Stats> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getServerInfo(me.endergaming.common.grpc.Communication.Empty request,
        io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Server_Info> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetServerInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CommunicationsBlockingStub extends io.grpc.stub.AbstractStub<CommunicationsBlockingStub> {
    private CommunicationsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommunicationsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommunicationsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommunicationsBlockingStub(channel, callOptions);
    }

    /**
     */
    public me.endergaming.common.grpc.Communication.Stats getStats(me.endergaming.common.grpc.Communication.PlayerStatsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public me.endergaming.common.grpc.Communication.Server_Info getServerInfo(me.endergaming.common.grpc.Communication.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetServerInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CommunicationsFutureStub extends io.grpc.stub.AbstractStub<CommunicationsFutureStub> {
    private CommunicationsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommunicationsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommunicationsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommunicationsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<me.endergaming.common.grpc.Communication.Stats> getStats(
        me.endergaming.common.grpc.Communication.PlayerStatsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<me.endergaming.common.grpc.Communication.Server_Info> getServerInfo(
        me.endergaming.common.grpc.Communication.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetServerInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STATS = 0;
  private static final int METHODID_GET_SERVER_INFO = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CommunicationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CommunicationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STATS:
          serviceImpl.getStats((me.endergaming.common.grpc.Communication.PlayerStatsRequest) request,
              (io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Stats>) responseObserver);
          break;
        case METHODID_GET_SERVER_INFO:
          serviceImpl.getServerInfo((me.endergaming.common.grpc.Communication.Empty) request,
              (io.grpc.stub.StreamObserver<me.endergaming.common.grpc.Communication.Server_Info>) responseObserver);
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

  private static abstract class CommunicationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CommunicationsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return me.endergaming.common.grpc.Communication.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Communications");
    }
  }

  private static final class CommunicationsFileDescriptorSupplier
      extends CommunicationsBaseDescriptorSupplier {
    CommunicationsFileDescriptorSupplier() {}
  }

  private static final class CommunicationsMethodDescriptorSupplier
      extends CommunicationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CommunicationsMethodDescriptorSupplier(String methodName) {
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
      synchronized (CommunicationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CommunicationsFileDescriptorSupplier())
              .addMethod(getGetStatsMethod())
              .addMethod(getGetServerInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
