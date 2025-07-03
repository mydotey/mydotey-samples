use super::ArrayInsert;

static MERGER1: _Merger1 = _Merger1;
static MERGER2: _Merger2 = _Merger2;

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

    let merger: &dyn Merger<T> = if len % 2 == 0 { &MERGER1 } else { &MERGER2 };
    merger.merge(arr, start, mid, end);
}

trait Merger<T: PartialOrd> {
    fn merge(&self, arr: &mut [T], start: usize, mid: usize, end: usize);
}

struct _Merger1;

impl<T: PartialOrd> Merger<T> for _Merger1 {
    fn merge(&self, arr: &mut [T], start: usize, mid: usize, end: usize) {
        let mut greater = start;
        for i in mid..end {
            for j in greater..i {
                greater += 1;
                if arr[j] > arr[i] {
                    arr.insert(i, j);
                    break;
                }
            }

            if greater == i {
                break;
            }
        }
    }
}

struct _Merger2;

impl<T: PartialOrd> Merger<T> for _Merger2 {
    fn merge(&self, arr: &mut [T], start: usize, mid: usize, end: usize) {
        let mut left = mid;
        for i in start..mid {
            if arr[i] > arr[mid] {
                left = i;
                break;
            }
        }
        if left == mid {
            return; // No need to merge if the left part is already sorted
        }

        let mut right = mid;
        for i in (mid..end).rev() {
            if arr[i] < arr[left] {
                right = i;
                break;
            }
        }

        let new_mid = right + 1;
        let new_start = left + (new_mid - mid);
        for i in mid..new_mid {
            arr.insert(i, left);
            left += 1;
        }

        self.merge(arr, new_start, new_mid, end);
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

    #[test]
    fn test_scope() {
        for i in 0..0 {
            println!("i: {}", i);
        }
    }
}
