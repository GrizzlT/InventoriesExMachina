package com.github.grizzlt.inventoriesexmachina.version.v1_13_R2;

import org.bukkit.inventory.meta.tags.ItemTagAdapterContext;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDTagType implements ItemTagType<byte[], UUID>
{
    /**
     * The one and only instance of this class.
     * Since this class stores no state information (apart from this field),
     * the usage of a single instance is safe even across multiple threads.
     */
    public static final UUIDTagType INSTANCE = new UUIDTagType();

    /**
     * A private constructor so that only a single instance of this class can exist.
     */
    private UUIDTagType() {}

    @Override
    public Class<byte[]> getPrimitiveType()
    {
        return byte[].class;
    }

    @Override
    public Class<UUID> getComplexType()
    {
        return UUID.class;
    }

    @Override
    public byte[] toPrimitive(UUID complex, ItemTagAdapterContext context)
    {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(complex.getMostSignificantBits());
        buffer.putLong(complex.getLeastSignificantBits());
        return buffer.array();
    }

    @Override
    public UUID fromPrimitive(byte[] primitive, ItemTagAdapterContext context)
    {
        ByteBuffer buffer = ByteBuffer.wrap(primitive);
        long most = buffer.getLong();
        long least = buffer.getLong();
        return new UUID(most, least);
    }
}
