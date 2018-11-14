//
//  ViewController.swift
//  iOSApp
//
//  Created by Sebastian on 13/11/2018.
//  Copyright © 2018 Sebastian. All rights reserved.
//

import UIKit
import app

class ViewController: UIViewController, MainView {
    
    private lazy var mainViewPresenter: MainPresenter = {
        return MainPresenter()
    }()
    
    func showElementAddedInfo() {
        
    }
    
    func showToDoList(toDoList: [String]) {
        
    }
    
    private let button: UIButton = {
        let button = UIButton(type: .system)
        button.backgroundColor = .blue
        button.setTitle("Add", for: .normal)
        button.setTitleColor(.white, for: .normal)
        button.translatesAutoresizingMaskIntoConstraints = false
        button.layer.cornerRadius = 10
        button.addTarget(self, action: #selector(addElement), for: .touchUpInside)
        return button
    }()
    
    
    @objc func addElement() {
        mainViewPresenter.addNewToDoElement(item: "Test123")
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addSubview(button)
        view.backgroundColor = .white
        
        button.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        button.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
        button.widthAnchor.constraint(equalToConstant: 150).isActive = true
        button.heightAnchor.constraint(equalToConstant: 30).isActive = true
    }
}
