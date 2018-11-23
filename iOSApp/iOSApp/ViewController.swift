//
//  ViewController.swift
//  iOSApp
//
//  Created by Sebastian on 21/11/2018.
//  Copyright © 2018 Sebastian. All rights reserved.
//

import UIKit
import app

class ViewController: UIViewController {
    
    var presenter: MainPresenter {
        return MainPresenter()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        presenter.addNewToDoElement(item: "ok")
    }
    
    


}

