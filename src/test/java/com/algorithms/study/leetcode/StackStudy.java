package com.algorithms.study.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2021-03-11
 * @Description
 */
public class StackStudy {
    //编写一个 StockSpanner 类，它收集某些股票的每日报价，并返回该股票当日价格的跨度。
    //
    // 今天股票价格的跨度被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
    //
    // 例如，如果未来7天股票的价格是 [100, 80, 60, 70, 60, 75, 85]，那么股票跨度将是 [1, 1, 1, 2, 1, 4, 6]。
    //
    //
    //
    //
    // 示例：
    //
    // 输入：["StockSpanner","next","next","next","next","next","next","next"], [[],[10
    //0],[80],[60],[70],[60],[75],[85]]
    //输出：[null,1,1,1,2,1,4,6]
    //解释：
    //首先，初始化 S = StockSpanner()，然后：
    //S.next(100) 被调用并返回 1，
    //S.next(80) 被调用并返回 1，
    //S.next(60) 被调用并返回 1，
    //S.next(70) 被调用并返回 2，
    //S.next(60) 被调用并返回 1，
    //S.next(75) 被调用并返回 4，
    //S.next(85) 被调用并返回 6。
    //
    //注意 (例如) S.next(75) 返回 4，因为截至今天的最后 4 个价格
    //(包括今天的价格 75) 小于或等于今天的价格。
    //
    //
    //
    //
    // 提示：
    //
    //
    // 调用 StockSpanner.next(int price) 时，将有 1 <= price <= 10^5。
    // 每个测试用例最多可以调用 10000 次 StockSpanner.next。
    // 在所有测试用例中，最多调用 150000 次 StockSpanner.next。
    // 此问题的总时间限制减少了 50%。
    //
    // Related Topics 栈
    // 👍 107 👎 0

    private static Stack<Integer> prices = new Stack<>();
    private static Stack<Integer> cache = new Stack<>();

    public static int next(int price) {
        int ans = 1;
        while (!prices.isEmpty() && prices.peek() <= price) {
            prices.pop();
            ans += cache.pop();
        }
        cache.push(ans);
        prices.push(price);
        return ans;
    }

    @Test
    public void test() {
        System.out.println(next(100));
        System.out.println(next(80));
        System.out.println(next(60));
        System.out.println(next(70));
        System.out.println(next(60));
        System.out.println(next(75));
        System.out.println(next(85));
    }

    //给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第
    //一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
    //
    // 示例 1:
    //
    //
    //输入: [1,2,1]
    //输出: [2,-1,2]
    //解释: 第一个 1 的下一个更大的数是 2；
    //数字 2 找不到下一个更大的数；
    //第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
    //
    //
    // 注意: 输入数组的长度不会超过 10000。
    // Related Topics 栈
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ret = new int[n];
        Arrays.fill(ret, -1);
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n * 2 - 1; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
                ret[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ret;
    }

    @Test
    public void test02() {
        int[] array = new int[]{1, 2, 3, 4, 3, 2, 5};
        Arrays.stream(nextGreaterElements(array)).forEach(x -> System.out.println(x));
    }

    //给定一个整数数组 A，你可以从某一起始索引出发，跳跃一定次数。在你跳跃的过程中，第 1、3、5... 次跳跃称为奇数跳跃，而第 2、4、6... 次跳跃称为
    //偶数跳跃。
    //
    // 你可以按以下方式从索引 i 向后跳转到索引 j（其中 i < j）：
    //
    //
    // 在进行奇数跳跃时（如，第 1，3，5... 次跳跃），你将会跳到索引 j，使得 A[i] <= A[j]，A[j] 是可能的最小值。如果存在多个这样的索引
    // j，你只能跳到满足要求的最小索引 j 上。
    // 在进行偶数跳跃时（如，第 2，4，6... 次跳跃），你将会跳到索引 j，使得 A[i] >= A[j]，A[j] 是可能的最大值。如果存在多个这样的索引
    // j，你只能跳到满足要求的最小索引 j 上。
    // （对于某些索引 i，可能无法进行合乎要求的跳跃。）
    //
    //
    // 如果从某一索引开始跳跃一定次数（可能是 0 次或多次），就可以到达数组的末尾（索引 A.length - 1），那么该索引就会被认为是好的起始索引。
    //
    // 返回好的起始索引的数量。
    //
    //
    //
    // 示例 1：
    //
    // 输入：[10,13,12,14,15]
    //输出：2
    //解释：
    //从起始索引 i = 0 出发，我们可以跳到 i = 2，（因为 A[2] 是 A[1]，A[2]，A[3]，A[4] 中大于或等于 A[0] 的最小值），然
    //后我们就无法继续跳下去了。
    //从起始索引 i = 1 和 i = 2 出发，我们可以跳到 i = 3，然后我们就无法继续跳下去了。
    //从起始索引 i = 3 出发，我们可以跳到 i = 4，到达数组末尾。
    //从起始索引 i = 4 出发，我们已经到达数组末尾。
    //总之，我们可以从 2 个不同的起始索引（i = 3, i = 4）出发，通过一定数量的跳跃到达数组末尾。
    //
    //
    // 示例 2：
    //
    // 输入：[2,3,1,1,4]
    //输出：3
    //解释：
    //从起始索引 i=0 出发，我们依次可以跳到 i = 1，i = 2，i = 3：
    //
    //在我们的第一次跳跃（奇数）中，我们先跳到 i = 1，因为 A[1] 是（A[1]，A[2]，A[3]，A[4]）中大于或等于 A[0] 的最小值。
    //
    //在我们的第二次跳跃（偶数）中，我们从 i = 1 跳到 i = 2，因为 A[2] 是（A[2]，A[3]，A[4]）中小于或等于 A[1] 的最大值。A[
    //3] 也是最大的值，但 2 是一个较小的索引，所以我们只能跳到 i = 2，而不能跳到 i = 3。
    //
    //在我们的第三次跳跃（奇数）中，我们从 i = 2 跳到 i = 3，因为 A[3] 是（A[3]，A[4]）中大于或等于 A[2] 的最小值。
    //
    //我们不能从 i = 3 跳到 i = 4，所以起始索引 i = 0 不是好的起始索引。
    //
    //类似地，我们可以推断：
    //从起始索引 i = 1 出发， 我们跳到 i = 4，这样我们就到达数组末尾。
    //从起始索引 i = 2 出发， 我们跳到 i = 3，然后我们就不能再跳了。
    //从起始索引 i = 3 出发， 我们跳到 i = 4，这样我们就到达数组末尾。
    //从起始索引 i = 4 出发，我们已经到达数组末尾。
    //总之，我们可以从 3 个不同的起始索引（i = 1, i = 3, i = 4）出发，通过一定数量的跳跃到达数组末尾。
    //
    //
    // 示例 3：
    //
    // 输入：[5,1,3,4,2]
    //输出：3
    //解释：
    //我们可以从起始索引 1，2，4 出发到达数组末尾。
    //
    //
    //
    //
    // 提示：
    //
    //
    // 1 <= A.length <= 20000
    // 0 <= A[i] < 100000
    //
    // Related Topics 栈 动态规划 Ordered Map
    public int oddEvenJumps2(Integer[] arr) {
        int N = arr.length;
        if (N <= 1) return N;
        boolean[] odd = new boolean[N];
        boolean[] even = new boolean[N];
        odd[N - 1] = even[N - 1] = true;

        TreeMap<Integer, Integer> vals = new TreeMap<>();
        vals.put(arr[N - 1], N - 1);
        for (int i = N - 2; i >= 0; --i) {
            int v = arr[i];
            if (vals.containsKey(v)) {
                odd[i] = even[vals.get(v)];
                even[i] = odd[vals.get(v)];
            } else {
                Integer lower = vals.lowerKey(v);
                Integer higher = vals.higherKey(v);

                if (lower != null)
                    even[i] = odd[vals.get(lower)];
                if (higher != null) {
                    odd[i] = even[vals.get(higher)];
                }
            }
            vals.put(v, i);
        }

        int ans = 0;
        for (boolean b : odd) {
            if (b) ans++;
        }
        return ans;
    }

    @Test
    public void test03() {
        Integer[] array = new Integer[]{5, 1, 3, 4, 2};
        System.out.println(oddEvenJumps2(array));
    }

}
