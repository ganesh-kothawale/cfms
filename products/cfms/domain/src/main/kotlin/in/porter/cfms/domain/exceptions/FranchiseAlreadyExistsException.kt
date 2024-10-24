package `in`.porter.cfms.domain.exceptions

class FranchiseAlreadyExistsException(email: String) :
  FranchiseException("Franchise with Franchise email $email already exists")
