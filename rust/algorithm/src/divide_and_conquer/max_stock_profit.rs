pub fn max_stock_profit(prices: &[f64]) -> f64 {
    let len = prices.len();
    match len {
        0 | 1 => return 0.0,
        _ => {
            let mut i = len - 1;
            let mut profit = 0.0;
            for j in (0..len - 1).rev() {
                if prices[j] >= prices[len - 1] {
                    profit = max_stock_profit(&prices[0..j + 1]);
                    break;
                }
                if prices[j] < prices[i] {
                    i = j;
                }
            }

            return if i == len - 1 {
                profit
            } else {
                profit + prices[len - 1] - prices[i]
            };
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_max_stock_profit() {
        let prices = vec![1.0, 2.0, 3.0, 4.0, 5.0];
        assert_eq!(max_stock_profit(&prices), 4.0);
        let prices = vec![5.0, 4.0, 3.0, 2.0, 1.0];
        assert_eq!(max_stock_profit(&prices), 0.0);
        let prices = vec![1.0, 2.0, 3.0, 2.0, 5.0];
        assert_eq!(max_stock_profit(&prices), 4.0);
        let prices = vec![1.0, 2.0, 3.0, 2.0, 1.0];
        assert_eq!(max_stock_profit(&prices), 2.0);
        let prices = vec![1.0, 2.0, 3.0, 2.0, 1.0, 4.0];
        assert_eq!(max_stock_profit(&prices), 3.0);
    }
}
