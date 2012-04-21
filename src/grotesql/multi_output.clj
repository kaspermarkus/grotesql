(ns grotesql.multi-output)

(defn partition-rows 
    "Lazy - Takes a table and a function as input. It returns two tables,
    with the first one containing the values for which the function
    returns true. The second table will contain all the rejected values.
    The two tables are returned in a list:
    '( '( table1 ) '( table2 ) ).

    The function runs (lazily) through the input table twice, which in my understanding
    is necessary if it is to be lazy: 
    http://stackoverflow.com/questions/8831585/more-efficient-split-with-in-clojure

    As the function is lazy, there should be no extra cost involved in running 
    this method only for using the first (or second) list. The unused list
    will never be evaluated and hence doesn't use any resources.

    partition-fn: The function that is used to partition the table. If running
    the functoin on a row yelds true, it will be put in the first table, else it
    will be in the second table
    table: The table to partition"
    ([ partition-fn table ]
        (list
            (filter partition-fn table)
            (filter (fn [x] (not (partition-fn x))) table))))