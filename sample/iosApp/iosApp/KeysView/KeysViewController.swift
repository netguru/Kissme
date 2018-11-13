//
//  KeysViewController.swift
//  iosApp
//

import UIKit

class KeysViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    private lazy var view: KeysView?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view?.tableView.delegate = self
        view?.tableView.dataSource = self
    }
    
    override func loadView() {
        view = KeysView()
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return UITableViewCell()
    }
}
