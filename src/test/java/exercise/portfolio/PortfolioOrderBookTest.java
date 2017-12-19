package exercise.portfolio;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioOrderBookTest {
    private static final String C_1 = "c1";
    private static final String C_2 = "c2";
    private PortfolioOrderBook target;
    @Mock
    private InstrumentOrderBookRepository instrumentOrderBookRepository;

    @Before
    public void setUp() throws Exception {
        when(instrumentOrderBookRepository.getOrderBook(C_1)).thenReturn(new InstrumentOrderBook(C_1,
                ImmutableList.of(new Order(100, 500), new Order(99, 400), new Order(98, 300)),
                ImmutableList.of(new Order(101, 200), new Order(102, 300), new Order(103, 100))));

        when(instrumentOrderBookRepository.getOrderBook(C_2)).thenReturn(new InstrumentOrderBook(C_2,
                ImmutableList.of(new Order(90, 400), new Order(89, 200), new Order(88, 100)),
                ImmutableList.of(new Order(91, 400), new Order(92, 600), new Order(93, 200))));

        target = new PortfolioOrderBook(instrumentOrderBookRepository);
        List<Constituent> constituents = Arrays.asList(
                new Constituent(C_1, 1, Side.BUY),
                new Constituent(C_2, 1, Side.SELL));
        target.setConstituents(constituents);
    }

    @Test
    public void computeBids() {
        List<Order> bids = target.computeBids();
        assertThat(bids.get(0).getPrice()).isEqualTo(9, within(0.01));
        assertThat(bids.get(0).getQuantity()).isEqualTo(400);
        assertThat(bids.get(1).getPrice()).isEqualTo(8, within(0.01));
        assertThat(bids.get(1).getQuantity()).isEqualTo(100);
        assertThat(bids.get(2).getPrice()).isEqualTo(7, within(0.01));
        assertThat(bids.get(2).getQuantity()).isEqualTo(400);
        assertThat(bids.get(3).getPrice()).isEqualTo(6, within(0.01));
        assertThat(bids.get(3).getQuantity()).isEqualTo(100);
        assertThat(bids.get(4).getPrice()).isEqualTo(5, within(0.01));
        assertThat(bids.get(4).getQuantity()).isEqualTo(200);
        assertThat(bids.size()).isEqualTo(5);
    }

    @Test
    public void computerOffers() {
        List<Order> offers = target.computerOffers();
        assertThat(offers.get(0).getPrice()).isEqualTo(11, within(0.01));
        assertThat(offers.get(0).getQuantity()).isEqualTo(200);
        assertThat(offers.get(1).getPrice()).isEqualTo(12, within(0.01));
        assertThat(offers.get(1).getQuantity()).isEqualTo(200);
        assertThat(offers.get(2).getPrice()).isEqualTo(13, within(0.01));
        assertThat(offers.get(2).getQuantity()).isEqualTo(100);
        assertThat(offers.get(3).getPrice()).isEqualTo(14, within(0.01));
        assertThat(offers.get(3).getQuantity()).isEqualTo(100);
        assertThat(offers.size()).isEqualTo(4);
    }

    @Test(expected = IllegalStateException.class)
    public void testMissingInstruemntOrderBook() {
        target = new PortfolioOrderBook(instrumentOrderBookRepository);
        List<Constituent> constituents = Arrays.asList(
                new Constituent(C_1, 1, Side.BUY),
                new Constituent("C3", 1, Side.SELL));
        target.setConstituents(constituents);
        target.computeBids();
    }

    // TODO test more use/edge case ... multiple buys/sells and different weight ...
}