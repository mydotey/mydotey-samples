use std::ptr::read;

pub fn merge_sort<T: PartialOrd>(arr: &mut [T]) {
    _merge_sort(arr, 0, arr.len());
}

fn _merge_sort<T: PartialOrd>(arr: &mut [T], start: usize, end: usize) {
    let len = end - start;
    if len <= 1 {
        return; // No need to sort if the array has 0 or 1 elements
    }

    let mid = start + len / 2;
    _merge_sort(arr, 0, mid);
    _merge_sort(arr, mid, end);

    let mut greater = start;
    for i in mid..end {
        for j in greater..i {
            greater += 1;
            if arr[j] > arr[i] {
                unsafe {
                    let temp = read(&arr[i] as *const T);
                    for k in (j..i).rev() {
                        arr[k + 1] = read(&arr[k] as *const T);
                    }
                    arr[j] = temp;
                }
                break;
            }
        }

        if greater == i {
            break;
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_merge_sort() {
        let mut arr = [64, 34, 25, 12, 22, 11, 90];
        merge_sort(&mut arr);
        assert_eq!(arr, [11, 12, 22, 25, 34, 64, 90]);
    }
}
