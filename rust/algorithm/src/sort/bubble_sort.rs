pub fn bubble_sort<T: PartialOrd>(arr: &mut [T]) {
    _bubble_sort(arr, arr.len());
}

fn _bubble_sort<T: PartialOrd>(arr: &mut [T], n: usize) {
    if n <= 1 {
        return; // No need to sort if the array has 0 or 1 elements
    }

    let mut swapped = false;
    for i in 0..n - 1 {
        if arr[i] > arr[i + 1] {
            arr.swap(i, i + 1);
            swapped = true;
        }
    }

    if !swapped {
        return; // If no elements were swapped, the array is sorted
    }

    _bubble_sort(arr, n - 1);
}

fn bubble_sort2<T: PartialOrd>(arr: &mut [T]) {
    let n = arr.len();
    for i in 0..n {
        let mut swapped = false;
        for j in 0..n - 1 - i {
            if arr[j] > arr[j + 1] {
                arr.swap(j, j + 1);
                swapped = true;
            }
        }
        if !swapped {
            break; // If no elements were swapped, the array is sorted
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_bubble_sort() {
        let mut arr = [64, 34, 25, 12, 22, 11, 90];
        bubble_sort(&mut arr);
        assert_eq!(arr, [11, 12, 22, 25, 34, 64, 90]);
    }

    #[test]
    fn test_bubble_sort2() {
        let mut arr = [64, 34, 25, 12, 22, 11, 90];
        bubble_sort2(&mut arr);
        assert_eq!(arr, [11, 12, 22, 25, 34, 64, 90]);
    }
}
