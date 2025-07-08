pub fn max<T: PartialOrd>(nums: &[T]) -> Option<&T> {
    if nums.is_empty() {
        return None;
    }
    if nums.len() == 1 {
        return Some(&nums[0]);
    }
    let max_of_sub = max(&nums[0..nums.len() - 1]);
    max_of_sub.map(|max| {
        if nums[nums.len() - 1] > *max {
            &nums[nums.len() - 1]
        } else {
            max
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_max() {
        let nums = vec![1, 2, 3, 4, 5];
        assert_eq!(max(&nums), Some(&5));
        let nums = vec![5, 4, 3, 2, 1];
        assert_eq!(max(&nums), Some(&5));
        let nums = vec![1, 2, 3, 4, 5, 6];
        assert_eq!(max(&nums), Some(&6));
    }
}
