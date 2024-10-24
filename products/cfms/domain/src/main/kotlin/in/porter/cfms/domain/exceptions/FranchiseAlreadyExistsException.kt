package `in`.porter.cfms.domain.exceptions

class FranchiseAlreadyExistsException(code: String) :
  FranchiseException("Franchise with Franchise email $code already exists")
