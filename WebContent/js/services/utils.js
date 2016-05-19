app.service('utils', ['$location','$route',function($location,$route) {
    var view = "";
    var category = "";
    this.getView = function() {
        return view;
    },
    this.setView = function(_view) {
        view = _view;
        $route.reload();
    },
    this.getCategory = function() {
        return category;
    },
    this.setCategory = function(_category) {
        category = _category;
        $route.reload();
    },
    this.estoy = function(ruta) {
        return $location.path() == ruta;
    }
}]);