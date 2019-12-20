package de.tutous.spring.boot.common.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import de.tutous.spring.boot.common.stream.StreamSupplier;

public class StreamSupplierTest
{

    @Test
    public void testMap()
    {
        LinkedList<String> expected = new LinkedList<String>(Arrays.asList("1", "2", "3", "4", "5", "6"));

        StreamSupplier<Integer> supplier = () -> Arrays.asList(1, 2, 3).stream();

        supplier.map(i -> String.valueOf(i))
                // add 4, 5 and 6
                .concat(Arrays.asList("4", "5", "6").stream())
                // check
                .forEach(v -> assertThat(v).isEqualTo(expected.pop()));
    }

}
