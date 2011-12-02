(ns grotesql.core
	(:use grotesql.functions))

(defmacro defnode
	"Create a node for the pipeline.
	Takes a table-function with parameters"
	[nname func & param] 
	`(def ~nname (partial ~func  ~@param)))

(defmacro defpipe
	"Take a list of nodes, and return a named function that
	takes the last table

	As for now, its the same as  create-pipe, 
	with ?correct? arguments
	"

	[pname & function_list]
	`(def ~pname (apply comp (reverse ~@function_list ))))
