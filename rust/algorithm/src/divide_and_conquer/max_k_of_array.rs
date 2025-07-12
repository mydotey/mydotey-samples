use std::collections::HashSet;

pub fn kth_max_of_array(arr: &[i32], k: usize) -> Option<i32> {
    if k == 0 || k > arr.len() {
        return None;
    }

    if k == 1 {
        let mut max = arr[0];
        for i in 1..arr.len() {
            if arr[i] > max {
                max = arr[i];
            }
        }
        return Some(max);
    }

    let sub_arr = &arr[0..arr.len() - 1];
    let last = arr[arr.len() - 1];
    let mut candidate = kth_max_of_array(sub_arr, k - 1).unwrap();
    if candidate <= last {
        return Some(candidate);
    }
    if k == arr.len() {
        return Some(last);
    }
    candidate = kth_max_of_array(sub_arr, k).unwrap();
    return if candidate >= last {
        Some(candidate)
    } else {
        Some(last)
    };
}

pub fn kth_max_of_array2(arr: &[i32], k: usize) -> Option<i32> {
    if k == 0 || k > arr.len() {
        return None;
    }

    let mut remains_set = HashSet::<usize>::new();
    remains_set.extend(0..arr.len());
    let mut max_set = HashSet::<usize>::new();
    loop {
        let mut index = None;
        for i in remains_set.iter() {
            if index == None {
                index = Some(*i);
                continue;
            }

            if arr[index.unwrap()] < arr[*i] {
                index = Some(*i);
            }
        }
        let index = index.unwrap();
        remains_set.remove(&index);
        max_set.insert(index);
        if max_set.len() == k {
            return Some(arr[index]);
        }
    }
}

struct Solution;

impl Solution {
    fn find_kth_largest(nums: Vec<i32>, k: i32) -> i32 {
        kth_max_of_array(&nums[0..nums.len()], k.try_into().unwrap_or_default()).unwrap_or_default()
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_kth_max_of_array() {
        let arr = [1, 2, 3, 4, 5];
        assert_eq!(kth_max_of_array(&arr, 1), Some(5));
        assert_eq!(kth_max_of_array(&arr, 2), Some(4));
        assert_eq!(kth_max_of_array(&arr, 3), Some(3));
        assert_eq!(kth_max_of_array(&arr, 4), Some(2));
        assert_eq!(kth_max_of_array(&arr, 5), Some(1));
    }

    #[test]
    fn test_kth_max_of_array2() {
        let arr = [1, 2, 2, 4, 4];
        assert_eq!(kth_max_of_array(&arr, 1), Some(4));
        assert_eq!(kth_max_of_array(&arr, 2), Some(4));
        assert_eq!(kth_max_of_array(&arr, 3), Some(2));
        assert_eq!(kth_max_of_array(&arr, 4), Some(2));
        assert_eq!(kth_max_of_array(&arr, 5), Some(1));
    }

    #[test]
    fn test_kth_max_of_array3() {
        let arr = [-1, 2, 0];
        assert_eq!(kth_max_of_array(&arr, 1), Some(2));
        assert_eq!(kth_max_of_array(&arr, 2), Some(0));
        assert_eq!(kth_max_of_array(&arr, 3), Some(-1));
    }

    #[test]
    fn test_find_kth_largest() {
        let arr = vec![1, 2, 3, 4, 5];
        assert_eq!(Solution::find_kth_largest(arr.clone(), 0), 0);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 1), 5);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 2), 4);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 3), 3);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 4), 2);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 5), 1);
        assert_eq!(Solution::find_kth_largest(arr.clone(), 6), 0);
    }

    #[test]
    fn test_kth_max_of_array2_1() {
        let arr = [-1, 2, 0];
        assert_eq!(kth_max_of_array2(&arr, 1), Some(2));
        assert_eq!(kth_max_of_array2(&arr, 2), Some(0));
        assert_eq!(kth_max_of_array2(&arr, 3), Some(-1));
    }
}
