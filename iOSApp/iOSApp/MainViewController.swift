//
//  MainViewController.swift
//  iOSApp
//

import UIKit
import app
import keychainwrapper

class MainViewController: UIViewController, MainView {
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var tableView: UITableView!
    
    private var toDoList = [String]()
    
    private lazy var presenter: MainPresenter = {
        MainPresenter()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "Cell")
        
        presenter.attachView(view: self)
    }
    
    @IBAction func saveButtonPressed(_ sender: Any) {
        guard let passwordText = passwordTextField.text, !passwordText.isEmpty else {
            return
        }
        
        presenter.addNewToDoElement(item: passwordText)
    }
    
    func showElementAddedInfo() {
        let alertController = UIAlertController(title: "Success!", message: "Added password to keychain!", preferredStyle: .alert)
        let alertAction = UIAlertAction(title: "Ok", style: .default)
        alertController.addAction(alertAction)
        passwordTextField.text = ""
        self.present(alertController, animated: true, completion: nil)
    }
    
    func showToDoList(toDoList: [String]) {
        self.toDoList = toDoList
        tableView.reloadData()
    }
    
}

extension MainViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return toDoList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "Cell") else { return UITableViewCell() }
        cell.textLabel?.text = toDoList[indexPath.row]
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
}

