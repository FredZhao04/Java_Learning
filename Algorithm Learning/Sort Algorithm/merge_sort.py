def merge_sort(list):
    if len(list) < 2:
        return list
    else:
        mid = len(list) // 2
        lefthalf = list[:mid]
        righthalf = list[mid:]

        merge_sort(lefthalf)
        merge_sort(righthalf)

        i = 0
        j = 0
        k = 0
        while i < len(lefthalf) and j < len(righthalf):
            if lefthalf[i] < righthalf[j]:
                list[k] = lefthalf[i]
                i += 1
                k += 1
            else:
                list[k] = righthalf[j]
                j += 1
                k += 1
        while i < len(lefthalf):
            list[k] = lefthalf[i]
            i += 1
            k += 1
        while j < len(righthalf):
            list[k] = righthalf[j]
            j += 1
            k ++ 1

if __name__ == '__main__':
    list = [54,26,93,17,77,31,44,55,20]
    merge_sort(list)
    print(list)

