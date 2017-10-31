// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/proto/seeder_factory.proto

package route;

/**
 * Protobuf type {@code route.VideoMessage}
 */
public  final class VideoMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:route.VideoMessage)
    VideoMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use VideoMessage.newBuilder() to construct.
  private VideoMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private VideoMessage() {
    name_ = "";
    bitrate_ = 0;
    keyword_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private VideoMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 18: {
            route.SizeMessage.Builder subBuilder = null;
            if (size_ != null) {
              subBuilder = size_.toBuilder();
            }
            size_ = input.readMessage(route.SizeMessage.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(size_);
              size_ = subBuilder.buildPartial();
            }

            break;
          }
          case 24: {

            bitrate_ = input.readInt32();
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
              keyword_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000008;
            }
            keyword_.add(s);
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
        keyword_ = keyword_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return route.SeederFactoryProto.internal_static_route_VideoMessage_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return route.SeederFactoryProto.internal_static_route_VideoMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            route.VideoMessage.class, route.VideoMessage.Builder.class);
  }

  private int bitField0_;
  public static final int NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object name_;
  /**
   * <code>string name = 1;</code>
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>string name = 1;</code>
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SIZE_FIELD_NUMBER = 2;
  private route.SizeMessage size_;
  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  public boolean hasSize() {
    return size_ != null;
  }
  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  public route.SizeMessage getSize() {
    return size_ == null ? route.SizeMessage.getDefaultInstance() : size_;
  }
  /**
   * <code>.route.SizeMessage size = 2;</code>
   */
  public route.SizeMessageOrBuilder getSizeOrBuilder() {
    return getSize();
  }

  public static final int BITRATE_FIELD_NUMBER = 3;
  private int bitrate_;
  /**
   * <code>int32 bitrate = 3;</code>
   */
  public int getBitrate() {
    return bitrate_;
  }

  public static final int KEYWORD_FIELD_NUMBER = 4;
  private com.google.protobuf.LazyStringList keyword_;
  /**
   * <code>repeated string keyword = 4;</code>
   */
  public com.google.protobuf.ProtocolStringList
      getKeywordList() {
    return keyword_;
  }
  /**
   * <code>repeated string keyword = 4;</code>
   */
  public int getKeywordCount() {
    return keyword_.size();
  }
  /**
   * <code>repeated string keyword = 4;</code>
   */
  public java.lang.String getKeyword(int index) {
    return keyword_.get(index);
  }
  /**
   * <code>repeated string keyword = 4;</code>
   */
  public com.google.protobuf.ByteString
      getKeywordBytes(int index) {
    return keyword_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    if (size_ != null) {
      output.writeMessage(2, getSize());
    }
    if (bitrate_ != 0) {
      output.writeInt32(3, bitrate_);
    }
    for (int i = 0; i < keyword_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, keyword_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    if (size_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getSize());
    }
    if (bitrate_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, bitrate_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < keyword_.size(); i++) {
        dataSize += computeStringSizeNoTag(keyword_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getKeywordList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof route.VideoMessage)) {
      return super.equals(obj);
    }
    route.VideoMessage other = (route.VideoMessage) obj;

    boolean result = true;
    result = result && getName()
        .equals(other.getName());
    result = result && (hasSize() == other.hasSize());
    if (hasSize()) {
      result = result && getSize()
          .equals(other.getSize());
    }
    result = result && (getBitrate()
        == other.getBitrate());
    result = result && getKeywordList()
        .equals(other.getKeywordList());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    if (hasSize()) {
      hash = (37 * hash) + SIZE_FIELD_NUMBER;
      hash = (53 * hash) + getSize().hashCode();
    }
    hash = (37 * hash) + BITRATE_FIELD_NUMBER;
    hash = (53 * hash) + getBitrate();
    if (getKeywordCount() > 0) {
      hash = (37 * hash) + KEYWORD_FIELD_NUMBER;
      hash = (53 * hash) + getKeywordList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static route.VideoMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.VideoMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.VideoMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.VideoMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.VideoMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static route.VideoMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static route.VideoMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static route.VideoMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static route.VideoMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static route.VideoMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static route.VideoMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static route.VideoMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(route.VideoMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code route.VideoMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:route.VideoMessage)
      route.VideoMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return route.SeederFactoryProto.internal_static_route_VideoMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return route.SeederFactoryProto.internal_static_route_VideoMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              route.VideoMessage.class, route.VideoMessage.Builder.class);
    }

    // Construct using route.VideoMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      name_ = "";

      if (sizeBuilder_ == null) {
        size_ = null;
      } else {
        size_ = null;
        sizeBuilder_ = null;
      }
      bitrate_ = 0;

      keyword_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000008);
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return route.SeederFactoryProto.internal_static_route_VideoMessage_descriptor;
    }

    public route.VideoMessage getDefaultInstanceForType() {
      return route.VideoMessage.getDefaultInstance();
    }

    public route.VideoMessage build() {
      route.VideoMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public route.VideoMessage buildPartial() {
      route.VideoMessage result = new route.VideoMessage(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.name_ = name_;
      if (sizeBuilder_ == null) {
        result.size_ = size_;
      } else {
        result.size_ = sizeBuilder_.build();
      }
      result.bitrate_ = bitrate_;
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        keyword_ = keyword_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000008);
      }
      result.keyword_ = keyword_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof route.VideoMessage) {
        return mergeFrom((route.VideoMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(route.VideoMessage other) {
      if (other == route.VideoMessage.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.hasSize()) {
        mergeSize(other.getSize());
      }
      if (other.getBitrate() != 0) {
        setBitrate(other.getBitrate());
      }
      if (!other.keyword_.isEmpty()) {
        if (keyword_.isEmpty()) {
          keyword_ = other.keyword_;
          bitField0_ = (bitField0_ & ~0x00000008);
        } else {
          ensureKeywordIsMutable();
          keyword_.addAll(other.keyword_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      route.VideoMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (route.VideoMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object name_ = "";
    /**
     * <code>string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private route.SizeMessage size_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        route.SizeMessage, route.SizeMessage.Builder, route.SizeMessageOrBuilder> sizeBuilder_;
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public boolean hasSize() {
      return sizeBuilder_ != null || size_ != null;
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public route.SizeMessage getSize() {
      if (sizeBuilder_ == null) {
        return size_ == null ? route.SizeMessage.getDefaultInstance() : size_;
      } else {
        return sizeBuilder_.getMessage();
      }
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public Builder setSize(route.SizeMessage value) {
      if (sizeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        size_ = value;
        onChanged();
      } else {
        sizeBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public Builder setSize(
        route.SizeMessage.Builder builderForValue) {
      if (sizeBuilder_ == null) {
        size_ = builderForValue.build();
        onChanged();
      } else {
        sizeBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public Builder mergeSize(route.SizeMessage value) {
      if (sizeBuilder_ == null) {
        if (size_ != null) {
          size_ =
            route.SizeMessage.newBuilder(size_).mergeFrom(value).buildPartial();
        } else {
          size_ = value;
        }
        onChanged();
      } else {
        sizeBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public Builder clearSize() {
      if (sizeBuilder_ == null) {
        size_ = null;
        onChanged();
      } else {
        size_ = null;
        sizeBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public route.SizeMessage.Builder getSizeBuilder() {
      
      onChanged();
      return getSizeFieldBuilder().getBuilder();
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    public route.SizeMessageOrBuilder getSizeOrBuilder() {
      if (sizeBuilder_ != null) {
        return sizeBuilder_.getMessageOrBuilder();
      } else {
        return size_ == null ?
            route.SizeMessage.getDefaultInstance() : size_;
      }
    }
    /**
     * <code>.route.SizeMessage size = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        route.SizeMessage, route.SizeMessage.Builder, route.SizeMessageOrBuilder> 
        getSizeFieldBuilder() {
      if (sizeBuilder_ == null) {
        sizeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            route.SizeMessage, route.SizeMessage.Builder, route.SizeMessageOrBuilder>(
                getSize(),
                getParentForChildren(),
                isClean());
        size_ = null;
      }
      return sizeBuilder_;
    }

    private int bitrate_ ;
    /**
     * <code>int32 bitrate = 3;</code>
     */
    public int getBitrate() {
      return bitrate_;
    }
    /**
     * <code>int32 bitrate = 3;</code>
     */
    public Builder setBitrate(int value) {
      
      bitrate_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 bitrate = 3;</code>
     */
    public Builder clearBitrate() {
      
      bitrate_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList keyword_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureKeywordIsMutable() {
      if (!((bitField0_ & 0x00000008) == 0x00000008)) {
        keyword_ = new com.google.protobuf.LazyStringArrayList(keyword_);
        bitField0_ |= 0x00000008;
       }
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getKeywordList() {
      return keyword_.getUnmodifiableView();
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public int getKeywordCount() {
      return keyword_.size();
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public java.lang.String getKeyword(int index) {
      return keyword_.get(index);
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public com.google.protobuf.ByteString
        getKeywordBytes(int index) {
      return keyword_.getByteString(index);
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public Builder setKeyword(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureKeywordIsMutable();
      keyword_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public Builder addKeyword(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureKeywordIsMutable();
      keyword_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public Builder addAllKeyword(
        java.lang.Iterable<java.lang.String> values) {
      ensureKeywordIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, keyword_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public Builder clearKeyword() {
      keyword_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string keyword = 4;</code>
     */
    public Builder addKeywordBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureKeywordIsMutable();
      keyword_.add(value);
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:route.VideoMessage)
  }

  // @@protoc_insertion_point(class_scope:route.VideoMessage)
  private static final route.VideoMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new route.VideoMessage();
  }

  public static route.VideoMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<VideoMessage>
      PARSER = new com.google.protobuf.AbstractParser<VideoMessage>() {
    public VideoMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new VideoMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<VideoMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<VideoMessage> getParserForType() {
    return PARSER;
  }

  public route.VideoMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

