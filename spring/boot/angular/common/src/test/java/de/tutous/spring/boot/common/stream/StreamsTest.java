package de.tutous.spring.boot.common.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import de.tutous.spring.boot.common.stream.StreamSupplier;
import de.tutous.spring.boot.common.stream.Streams;

public class StreamsTest
{

    @Test
    public void testAsStreamIterable()
    {

        LinkedList<Integer> expected = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));

        StreamSupplier<Integer> stream1 = Streams.asStream(Arrays.asList(0, 1, 2), i -> i + 1);
        StreamSupplier<Integer> stream2 = Streams.asStream(Arrays.asList(3, 4, 5), i -> i + 1);

        stream1.concat(stream2).forEach(v -> assertThat(v).isEqualTo(expected.pop()));

    }

    @Test
    public void testAsStreamSet()
    {

        LinkedList<Integer> expected = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));

        StreamSupplier<Integer> stream1 = Streams.asStream(new HashSet<Integer>(Arrays.asList(0, 1, 2)), i -> i + 1);
        StreamSupplier<Integer> stream2 = Streams.asStream(new HashSet<Integer>(Arrays.asList(3, 4, 5)), i -> i + 1);

        stream1.concat(stream2).forEach(v -> assertThat(v).isEqualTo(expected.pop()));

    }

}
