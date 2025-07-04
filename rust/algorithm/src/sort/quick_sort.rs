pub fn quick_sort<T: Ord>(arr: &mut [T]) {
    if arr.len() <= 1 {
        return;
    }

    let pivot_index = partition(arr);
    quick_sort(&mut arr[0..pivot_index]);
    quick_sort(&mut arr[pivot_index + 1..]);
}

fn partition<T: Ord>(arr: &mut [T]) -> usize {
    let mut pivot_index = arr.len() / 2;
    let (mut i, mut j) = (0, arr.len());
    while i < j {
        for k in i..pivot_index {
            if arr[k] > arr[pivot_index] {
                arr.swap(k, pivot_index);
                pivot_index = k;
                break;
            }
        }
        i = pivot_index + 1;

        for k in (i..j).rev() {
            if arr[k] < arr[pivot_index] {
                arr.swap(k, pivot_index);
                pivot_index = k;
                break;
            }
        }
        j = pivot_index;
    }
    pivot_index
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_scope() {
        let s = 10..1;
        println!("s: {:?}", s);
        for i in s {
            println!("i: {}", i);
        }

        let s = 1..1;
        println!("s: {:?}", s);
        for i in s {
            println!("i: {}", i);
        }
    }

    #[test]
    fn test_quick_sort() {
        let mut arr = [3, 6, 8, 10, 11, 2, 9];
        quick_sort(&mut arr);
        assert_eq!(arr, [2, 3, 6, 8, 9, 10, 11]);
    }
}
