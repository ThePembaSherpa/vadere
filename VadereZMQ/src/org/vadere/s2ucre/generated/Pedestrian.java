// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: accurate_internal.proto

package org.vadere.s2ucre.generated;

public final class Pedestrian {
  private Pedestrian() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PedMsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:s2ucre.PedMsg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    boolean hasTime();
    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    com.google.protobuf.Timestamp getTime();
    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    com.google.protobuf.TimestampOrBuilder getTimeOrBuilder();

    /**
     * <code>int32 ped_id = 2;</code>
     */
    int getPedId();

    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    boolean hasPosition();
    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    org.vadere.s2ucre.generated.Common.UTMCoordinate getPosition();
    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder getPositionOrBuilder();

    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    boolean hasVelocity();
    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    org.vadere.s2ucre.generated.Common.Point3D getVelocity();
    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    org.vadere.s2ucre.generated.Common.Point3DOrBuilder getVelocityOrBuilder();
  }
  /**
   * <pre>
   * Data about a single pedestrian 
   * </pre>
   *
   * Protobuf type {@code org.vadere.s2ucre.generated.PedMsg}
   */
  public  static final class PedMsg extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:s2ucre.PedMsg)
      PedMsgOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use PedMsg.newBuilder() to construct.
    private PedMsg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PedMsg() {
      pedId_ = 0;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private PedMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
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
              com.google.protobuf.Timestamp.Builder subBuilder = null;
              if (time_ != null) {
                subBuilder = time_.toBuilder();
              }
              time_ = input.readMessage(com.google.protobuf.Timestamp.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(time_);
                time_ = subBuilder.buildPartial();
              }

              break;
            }
            case 16: {

              pedId_ = input.readInt32();
              break;
            }
            case 26: {
              org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder subBuilder = null;
              if (position_ != null) {
                subBuilder = position_.toBuilder();
              }
              position_ = input.readMessage(org.vadere.s2ucre.generated.Common.UTMCoordinate.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(position_);
                position_ = subBuilder.buildPartial();
              }

              break;
            }
            case 34: {
              org.vadere.s2ucre.generated.Common.Point3D.Builder subBuilder = null;
              if (velocity_ != null) {
                subBuilder = velocity_.toBuilder();
              }
              velocity_ = input.readMessage(org.vadere.s2ucre.generated.Common.Point3D.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(velocity_);
                velocity_ = subBuilder.buildPartial();
              }

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
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.vadere.s2ucre.generated.Pedestrian.internal_static_s2ucre_PedMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.vadere.s2ucre.generated.Pedestrian.internal_static_s2ucre_PedMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.vadere.s2ucre.generated.Pedestrian.PedMsg.class, org.vadere.s2ucre.generated.Pedestrian.PedMsg.Builder.class);
    }

    public static final int TIME_FIELD_NUMBER = 1;
    private com.google.protobuf.Timestamp time_;
    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    public boolean hasTime() {
      return time_ != null;
    }
    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    public com.google.protobuf.Timestamp getTime() {
      return time_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : time_;
    }
    /**
     * <code>.google.protobuf.Timestamp time = 1;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getTimeOrBuilder() {
      return getTime();
    }

    public static final int PED_ID_FIELD_NUMBER = 2;
    private int pedId_;
    /**
     * <code>int32 ped_id = 2;</code>
     */
    public int getPedId() {
      return pedId_;
    }

    public static final int POSITION_FIELD_NUMBER = 3;
    private org.vadere.s2ucre.generated.Common.UTMCoordinate position_;
    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    public boolean hasPosition() {
      return position_ != null;
    }
    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    public org.vadere.s2ucre.generated.Common.UTMCoordinate getPosition() {
      return position_ == null ? org.vadere.s2ucre.generated.Common.UTMCoordinate.getDefaultInstance() : position_;
    }
    /**
     * <code>.s2ucre.UTMCoordinate position = 3;</code>
     */
    public org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder getPositionOrBuilder() {
      return getPosition();
    }

    public static final int VELOCITY_FIELD_NUMBER = 4;
    private org.vadere.s2ucre.generated.Common.Point3D velocity_;
    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    public boolean hasVelocity() {
      return velocity_ != null;
    }
    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    public org.vadere.s2ucre.generated.Common.Point3D getVelocity() {
      return velocity_ == null ? org.vadere.s2ucre.generated.Common.Point3D.getDefaultInstance() : velocity_;
    }
    /**
     * <pre>
     * covers direction and magnitude (vector length = speed)
     * </pre>
     *
     * <code>.s2ucre.Point3D velocity = 4;</code>
     */
    public org.vadere.s2ucre.generated.Common.Point3DOrBuilder getVelocityOrBuilder() {
      return getVelocity();
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
      if (time_ != null) {
        output.writeMessage(1, getTime());
      }
      if (pedId_ != 0) {
        output.writeInt32(2, pedId_);
      }
      if (position_ != null) {
        output.writeMessage(3, getPosition());
      }
      if (velocity_ != null) {
        output.writeMessage(4, getVelocity());
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (time_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getTime());
      }
      if (pedId_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, pedId_);
      }
      if (position_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, getPosition());
      }
      if (velocity_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(4, getVelocity());
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof org.vadere.s2ucre.generated.Pedestrian.PedMsg)) {
        return super.equals(obj);
      }
      org.vadere.s2ucre.generated.Pedestrian.PedMsg other = (org.vadere.s2ucre.generated.Pedestrian.PedMsg) obj;

      boolean result = true;
      result = result && (hasTime() == other.hasTime());
      if (hasTime()) {
        result = result && getTime()
            .equals(other.getTime());
      }
      result = result && (getPedId()
          == other.getPedId());
      result = result && (hasPosition() == other.hasPosition());
      if (hasPosition()) {
        result = result && getPosition()
            .equals(other.getPosition());
      }
      result = result && (hasVelocity() == other.hasVelocity());
      if (hasVelocity()) {
        result = result && getVelocity()
            .equals(other.getVelocity());
      }
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasTime()) {
        hash = (37 * hash) + TIME_FIELD_NUMBER;
        hash = (53 * hash) + getTime().hashCode();
      }
      hash = (37 * hash) + PED_ID_FIELD_NUMBER;
      hash = (53 * hash) + getPedId();
      if (hasPosition()) {
        hash = (37 * hash) + POSITION_FIELD_NUMBER;
        hash = (53 * hash) + getPosition().hashCode();
      }
      if (hasVelocity()) {
        hash = (37 * hash) + VELOCITY_FIELD_NUMBER;
        hash = (53 * hash) + getVelocity().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg parseFrom(
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
    public static Builder newBuilder(org.vadere.s2ucre.generated.Pedestrian.PedMsg prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * Data about a single pedestrian 
     * </pre>
     *
     * Protobuf type {@code org.vadere.s2ucre.generated.PedMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:s2ucre.PedMsg)
        org.vadere.s2ucre.generated.Pedestrian.PedMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.vadere.s2ucre.generated.Pedestrian.internal_static_s2ucre_PedMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.vadere.s2ucre.generated.Pedestrian.internal_static_s2ucre_PedMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.vadere.s2ucre.generated.Pedestrian.PedMsg.class, org.vadere.s2ucre.generated.Pedestrian.PedMsg.Builder.class);
      }

      // Construct using org.vadere.s2ucre.generated.Pedestrian.PedMsg.newBuilder()
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
        if (timeBuilder_ == null) {
          time_ = null;
        } else {
          time_ = null;
          timeBuilder_ = null;
        }
        pedId_ = 0;

        if (positionBuilder_ == null) {
          position_ = null;
        } else {
          position_ = null;
          positionBuilder_ = null;
        }
        if (velocityBuilder_ == null) {
          velocity_ = null;
        } else {
          velocity_ = null;
          velocityBuilder_ = null;
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.vadere.s2ucre.generated.Pedestrian.internal_static_s2ucre_PedMsg_descriptor;
      }

      public org.vadere.s2ucre.generated.Pedestrian.PedMsg getDefaultInstanceForType() {
        return org.vadere.s2ucre.generated.Pedestrian.PedMsg.getDefaultInstance();
      }

      public org.vadere.s2ucre.generated.Pedestrian.PedMsg build() {
        org.vadere.s2ucre.generated.Pedestrian.PedMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public org.vadere.s2ucre.generated.Pedestrian.PedMsg buildPartial() {
        org.vadere.s2ucre.generated.Pedestrian.PedMsg result = new org.vadere.s2ucre.generated.Pedestrian.PedMsg(this);
        if (timeBuilder_ == null) {
          result.time_ = time_;
        } else {
          result.time_ = timeBuilder_.build();
        }
        result.pedId_ = pedId_;
        if (positionBuilder_ == null) {
          result.position_ = position_;
        } else {
          result.position_ = positionBuilder_.build();
        }
        if (velocityBuilder_ == null) {
          result.velocity_ = velocity_;
        } else {
          result.velocity_ = velocityBuilder_.build();
        }
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
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
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.vadere.s2ucre.generated.Pedestrian.PedMsg) {
          return mergeFrom((org.vadere.s2ucre.generated.Pedestrian.PedMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.vadere.s2ucre.generated.Pedestrian.PedMsg other) {
        if (other == org.vadere.s2ucre.generated.Pedestrian.PedMsg.getDefaultInstance()) return this;
        if (other.hasTime()) {
          mergeTime(other.getTime());
        }
        if (other.getPedId() != 0) {
          setPedId(other.getPedId());
        }
        if (other.hasPosition()) {
          mergePosition(other.getPosition());
        }
        if (other.hasVelocity()) {
          mergeVelocity(other.getVelocity());
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
        org.vadere.s2ucre.generated.Pedestrian.PedMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.vadere.s2ucre.generated.Pedestrian.PedMsg) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private com.google.protobuf.Timestamp time_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> timeBuilder_;
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public boolean hasTime() {
        return timeBuilder_ != null || time_ != null;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public com.google.protobuf.Timestamp getTime() {
        if (timeBuilder_ == null) {
          return time_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : time_;
        } else {
          return timeBuilder_.getMessage();
        }
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public Builder setTime(com.google.protobuf.Timestamp value) {
        if (timeBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          time_ = value;
          onChanged();
        } else {
          timeBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public Builder setTime(
          com.google.protobuf.Timestamp.Builder builderForValue) {
        if (timeBuilder_ == null) {
          time_ = builderForValue.build();
          onChanged();
        } else {
          timeBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public Builder mergeTime(com.google.protobuf.Timestamp value) {
        if (timeBuilder_ == null) {
          if (time_ != null) {
            time_ =
              com.google.protobuf.Timestamp.newBuilder(time_).mergeFrom(value).buildPartial();
          } else {
            time_ = value;
          }
          onChanged();
        } else {
          timeBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public Builder clearTime() {
        if (timeBuilder_ == null) {
          time_ = null;
          onChanged();
        } else {
          time_ = null;
          timeBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public com.google.protobuf.Timestamp.Builder getTimeBuilder() {
        
        onChanged();
        return getTimeFieldBuilder().getBuilder();
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      public com.google.protobuf.TimestampOrBuilder getTimeOrBuilder() {
        if (timeBuilder_ != null) {
          return timeBuilder_.getMessageOrBuilder();
        } else {
          return time_ == null ?
              com.google.protobuf.Timestamp.getDefaultInstance() : time_;
        }
      }
      /**
       * <code>.google.protobuf.Timestamp time = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
          getTimeFieldBuilder() {
        if (timeBuilder_ == null) {
          timeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                  getTime(),
                  getParentForChildren(),
                  isClean());
          time_ = null;
        }
        return timeBuilder_;
      }

      private int pedId_ ;
      /**
       * <code>int32 ped_id = 2;</code>
       */
      public int getPedId() {
        return pedId_;
      }
      /**
       * <code>int32 ped_id = 2;</code>
       */
      public Builder setPedId(int value) {
        
        pedId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 ped_id = 2;</code>
       */
      public Builder clearPedId() {
        
        pedId_ = 0;
        onChanged();
        return this;
      }

      private org.vadere.s2ucre.generated.Common.UTMCoordinate position_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          org.vadere.s2ucre.generated.Common.UTMCoordinate, org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder, org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder> positionBuilder_;
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public boolean hasPosition() {
        return positionBuilder_ != null || position_ != null;
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public org.vadere.s2ucre.generated.Common.UTMCoordinate getPosition() {
        if (positionBuilder_ == null) {
          return position_ == null ? org.vadere.s2ucre.generated.Common.UTMCoordinate.getDefaultInstance() : position_;
        } else {
          return positionBuilder_.getMessage();
        }
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public Builder setPosition(org.vadere.s2ucre.generated.Common.UTMCoordinate value) {
        if (positionBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          position_ = value;
          onChanged();
        } else {
          positionBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public Builder setPosition(
          org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder builderForValue) {
        if (positionBuilder_ == null) {
          position_ = builderForValue.build();
          onChanged();
        } else {
          positionBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public Builder mergePosition(org.vadere.s2ucre.generated.Common.UTMCoordinate value) {
        if (positionBuilder_ == null) {
          if (position_ != null) {
            position_ =
              org.vadere.s2ucre.generated.Common.UTMCoordinate.newBuilder(position_).mergeFrom(value).buildPartial();
          } else {
            position_ = value;
          }
          onChanged();
        } else {
          positionBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public Builder clearPosition() {
        if (positionBuilder_ == null) {
          position_ = null;
          onChanged();
        } else {
          position_ = null;
          positionBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder getPositionBuilder() {
        
        onChanged();
        return getPositionFieldBuilder().getBuilder();
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      public org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder getPositionOrBuilder() {
        if (positionBuilder_ != null) {
          return positionBuilder_.getMessageOrBuilder();
        } else {
          return position_ == null ?
              org.vadere.s2ucre.generated.Common.UTMCoordinate.getDefaultInstance() : position_;
        }
      }
      /**
       * <code>.s2ucre.UTMCoordinate position = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          org.vadere.s2ucre.generated.Common.UTMCoordinate, org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder, org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder> 
          getPositionFieldBuilder() {
        if (positionBuilder_ == null) {
          positionBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              org.vadere.s2ucre.generated.Common.UTMCoordinate, org.vadere.s2ucre.generated.Common.UTMCoordinate.Builder, org.vadere.s2ucre.generated.Common.UTMCoordinateOrBuilder>(
                  getPosition(),
                  getParentForChildren(),
                  isClean());
          position_ = null;
        }
        return positionBuilder_;
      }

      private org.vadere.s2ucre.generated.Common.Point3D velocity_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          org.vadere.s2ucre.generated.Common.Point3D, org.vadere.s2ucre.generated.Common.Point3D.Builder, org.vadere.s2ucre.generated.Common.Point3DOrBuilder> velocityBuilder_;
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public boolean hasVelocity() {
        return velocityBuilder_ != null || velocity_ != null;
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public org.vadere.s2ucre.generated.Common.Point3D getVelocity() {
        if (velocityBuilder_ == null) {
          return velocity_ == null ? org.vadere.s2ucre.generated.Common.Point3D.getDefaultInstance() : velocity_;
        } else {
          return velocityBuilder_.getMessage();
        }
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public Builder setVelocity(org.vadere.s2ucre.generated.Common.Point3D value) {
        if (velocityBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          velocity_ = value;
          onChanged();
        } else {
          velocityBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public Builder setVelocity(
          org.vadere.s2ucre.generated.Common.Point3D.Builder builderForValue) {
        if (velocityBuilder_ == null) {
          velocity_ = builderForValue.build();
          onChanged();
        } else {
          velocityBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public Builder mergeVelocity(org.vadere.s2ucre.generated.Common.Point3D value) {
        if (velocityBuilder_ == null) {
          if (velocity_ != null) {
            velocity_ =
              org.vadere.s2ucre.generated.Common.Point3D.newBuilder(velocity_).mergeFrom(value).buildPartial();
          } else {
            velocity_ = value;
          }
          onChanged();
        } else {
          velocityBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public Builder clearVelocity() {
        if (velocityBuilder_ == null) {
          velocity_ = null;
          onChanged();
        } else {
          velocity_ = null;
          velocityBuilder_ = null;
        }

        return this;
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public org.vadere.s2ucre.generated.Common.Point3D.Builder getVelocityBuilder() {
        
        onChanged();
        return getVelocityFieldBuilder().getBuilder();
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      public org.vadere.s2ucre.generated.Common.Point3DOrBuilder getVelocityOrBuilder() {
        if (velocityBuilder_ != null) {
          return velocityBuilder_.getMessageOrBuilder();
        } else {
          return velocity_ == null ?
              org.vadere.s2ucre.generated.Common.Point3D.getDefaultInstance() : velocity_;
        }
      }
      /**
       * <pre>
       * covers direction and magnitude (vector length = speed)
       * </pre>
       *
       * <code>.s2ucre.Point3D velocity = 4;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          org.vadere.s2ucre.generated.Common.Point3D, org.vadere.s2ucre.generated.Common.Point3D.Builder, org.vadere.s2ucre.generated.Common.Point3DOrBuilder> 
          getVelocityFieldBuilder() {
        if (velocityBuilder_ == null) {
          velocityBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              org.vadere.s2ucre.generated.Common.Point3D, org.vadere.s2ucre.generated.Common.Point3D.Builder, org.vadere.s2ucre.generated.Common.Point3DOrBuilder>(
                  getVelocity(),
                  getParentForChildren(),
                  isClean());
          velocity_ = null;
        }
        return velocityBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:s2ucre.PedMsg)
    }

    // @@protoc_insertion_point(class_scope:s2ucre.PedMsg)
    private static final org.vadere.s2ucre.generated.Pedestrian.PedMsg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new org.vadere.s2ucre.generated.Pedestrian.PedMsg();
    }

    public static org.vadere.s2ucre.generated.Pedestrian.PedMsg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PedMsg>
        PARSER = new com.google.protobuf.AbstractParser<PedMsg>() {
      public PedMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PedMsg(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PedMsg> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<PedMsg> getParserForType() {
      return PARSER;
    }

    public org.vadere.s2ucre.generated.Pedestrian.PedMsg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_s2ucre_PedMsg_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_s2ucre_PedMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\020pedestrian.proto\022\006s2ucre\032\037google/proto" +
      "buf/timestamp.proto\032\014common.proto\"\216\001\n\006Pe" +
      "dMsg\022(\n\004time\030\001 \001(\0132\032.google.protobuf.Tim" +
      "estamp\022\016\n\006ped_id\030\002 \001(\005\022\'\n\010position\030\003 \001(\013" +
      "2\025.s2ucre.UTMCoordinate\022!\n\010velocity\030\004 \001(" +
      "\0132\017.s2ucre.Point3Db\006proto3"
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
          com.google.protobuf.TimestampProto.getDescriptor(),
          org.vadere.s2ucre.generated.Common.getDescriptor(),
        }, assigner);
    internal_static_s2ucre_PedMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_s2ucre_PedMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_s2ucre_PedMsg_descriptor,
        new String[] { "Time", "PedId", "Position", "Velocity", });
    com.google.protobuf.TimestampProto.getDescriptor();
    org.vadere.s2ucre.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
