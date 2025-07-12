pub fn kth_max_of_array(arr: &[i32], k: usize) -> Option<i32> {
    if k == 0 || k > arr.len() {
        return None;
    }

    if k == 1 {
        let mut max = arr[0];
        for i in 0 + 1..arr.len() {
            if arr[i] > arr[0] {
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
}
