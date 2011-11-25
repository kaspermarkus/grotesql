(ns grotesql.core
	(:use grotesql.functions))

(defmacro defnode
	"Create a node for the pipeline.
	Takes a table-function with parameters"
	[nname func & param] `(def ~nname (partial ~func  ~@param)))

;(defpipe)
